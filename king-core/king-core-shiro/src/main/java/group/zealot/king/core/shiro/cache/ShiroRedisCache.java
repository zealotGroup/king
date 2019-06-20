package group.zealot.king.core.shiro.cache;


import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.redis.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static group.zealot.king.core.shiro.ShiroConfig.timeout;

public class ShiroRedisCache implements Cache<Serializable, Object> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private RedisUtil redisUtil;
    private String keyPrefix;

    public ShiroRedisCache(RedisUtil redisUtil, String keyPrefix) {
        this.redisUtil = redisUtil;
        this.keyPrefix = keyPrefix;
    }

    private ValueOperations<Serializable, Object> valueOperations() {
        return redisUtil.valueOperations();
    }

    private RedisTemplate<Serializable, Object> redisTemplate() {
        return redisUtil.redisTemplate();
    }


    @Override
    public Object get(Serializable id) throws CacheException {
        String key = getKey(id);
        logger.debug("get 缓存key：" + key);
        return valueOperations().get(key);
    }

    @Override
    public Object put(Serializable id, Object value) throws CacheException {
        String key = getKey(id);
        String valueJSON = JSONObject.toJSONString(value);
        logger.debug("put 缓存key：" + key + " value:" + valueJSON + " =》先get缓存");
        Object old = get(key);
        valueOperations().set(key, value, timeout);
        return old;
    }

    @Override
    public Object remove(Serializable id) throws CacheException {
        String key = getKey(id);
        logger.debug("delete 缓存key：" + key + " =》先get缓存");
        Object old = get(id);
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
    public List<Object> values() {
        Set<Serializable> keys = keys();
        if (keys.isEmpty()) {
            return new ArrayList<>();
        }
        return redisTemplate().opsForValue().multiGet(keys);
    }

    private String getKey(Serializable id) {
        return keyPrefix + ":" + id;
    }
}
