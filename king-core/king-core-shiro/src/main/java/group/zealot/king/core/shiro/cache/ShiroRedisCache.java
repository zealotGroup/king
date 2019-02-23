package group.zealot.king.core.shiro.cache;


import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.redis.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static group.zealot.king.core.shiro.ShiroConfig.timeout;

public class ShiroRedisCache implements Cache<Serializable, SimpleSession> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private RedisUtil redisUtil;
    private String keyPrefix;

    public ShiroRedisCache(RedisUtil redisUtil, String keyPrefix) {
        this.redisUtil = redisUtil;
        this.keyPrefix = keyPrefix;
    }

    private ValueOperations<Serializable, String> valueOperations() {
        return redisUtil.valueOperations();

    }

    private RedisTemplate<Serializable, String> redisTemplate() {
        return redisUtil.redisTemplate();
    }


    @Override
    public SimpleSession get(Serializable id) throws CacheException {
        String key = getKey(id);
        logger.debug("get 缓存key：" + key);
        String value = valueOperations().get(key);
        return deserialize(value);
    }

    @Override
    public SimpleSession put(Serializable id, SimpleSession value) throws CacheException {
        String key = getKey(id);
        String valueJSON = serializer(value);
        logger.debug("put 缓存key：" + key + " value:" + valueJSON + " =》先get缓存");
        SimpleSession old = get(key);
        valueOperations().set(key, valueJSON, timeout);
        return old;
    }

    @Override
    public SimpleSession remove(Serializable id) throws CacheException {
        String key = getKey(id);
        logger.debug("delete 缓存key：" + key + " =》先get缓存");
        SimpleSession old = get(key);
        redisTemplate().delete(key);
        return old;
    }

    @Override
    public void clear() throws CacheException {
        Long size = valueOperations().size(keyPrefix);
        logger.debug("clear 缓存所有key，size：" + size);
        redisTemplate().delete(keyPrefix);
    }

    @Override
    public int size() {
        Long size = valueOperations().size(keyPrefix);
        logger.debug("size " + keyPrefix + "缓存所有key，size：" + size);
        return size == null ? 0 : size.intValue();
    }

    @Override
    public Set<Serializable> keys() {
        return redisTemplate().keys(keyPrefix);
    }

    @Override
    public List<SimpleSession> values() {
        Set<Serializable> keys = keys();
        if (keys.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> values = redisTemplate().opsForValue().multiGet(keys);
        if (values != null) {
            return deserialize(values);
        } else {
            return new ArrayList<>();
        }
    }

    private String getKey(Serializable id) {
        return keyPrefix + ":" + id;
    }

    private String serializer(SimpleSession simpleSession) {
        JSONObject jsonObject = new JSONObject();
        if (simpleSession != null) {
            jsonObject.put("id", simpleSession.getId());
            jsonObject.put("startTimestamp", simpleSession.getStartTimestamp());
            jsonObject.put("stopTimestamp", simpleSession.getStopTimestamp());
            jsonObject.put("lastAccessTime", simpleSession.getLastAccessTime());
            jsonObject.put("timeout", simpleSession.getTimeout());
            jsonObject.put("expired", simpleSession.isExpired());
            jsonObject.put("host", simpleSession.getHost());
            jsonObject.put("attributes", simpleSession.getAttributes());
        }
        return jsonObject.toJSONString();
    }

    private SimpleSession deserialize(String json) {
        logger.debug("deserialize json:" + json);
        if (json == null) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        SimpleSession simpleSession = new SimpleSession();
        simpleSession.setId(jsonObject.getObject("id", Serializable.class));
        simpleSession.setStartTimestamp(jsonObject.getDate("startTimestamp"));
        simpleSession.setStopTimestamp(jsonObject.getDate("stopTimestamp"));
        simpleSession.setLastAccessTime(jsonObject.getDate("lastAccessTime"));
        simpleSession.setTimeout(jsonObject.getLongValue("timeout"));
        simpleSession.setExpired(jsonObject.getBooleanValue("expired"));
        simpleSession.setHost(jsonObject.getString("host"));
        simpleSession.setAttributes((Map) jsonObject.get("attributes"));
        return simpleSession;
    }

    private List<SimpleSession> deserialize(List<String> list) {
        List<SimpleSession> values = new ArrayList<>(list.size());
        for (String json : list) {
            values.add(deserialize(json));
        }
        return values;
    }

}
