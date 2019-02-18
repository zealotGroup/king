package group.zealot.king.core.shiro.cache;

import group.zealot.king.core.zt.redis.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
public class ShiroRedisCache implements Cache<String, String> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtil redisUtil;
    private final String hashKey = "shiro:sessionid";

    private HashOperations<String, String, String> hashOperations() {
        return redisUtil.hashOperations();
    }

    @Override
    public String get(String key) throws CacheException {
        logger.debug("get shiro缓存key：" + key);
        return hashOperations().get(hashKey, key);
    }

    @Override
    public String put(String key, String value) throws CacheException {
        logger.debug("put shiro缓存key：" + key + "，value：" + value);
        String old = get(key);
        hashOperations().put(hashKey, key, value);
        return old;
    }

    @Override
    public String remove(String key) throws CacheException {
        logger.debug("delete shiro缓存key：" + key);
        String old = get(key);
        hashOperations().delete(hashKey, key);
        return old;
    }

    @Override
    public void clear() throws CacheException {
        Long size = hashOperations().size(hashKey);
        logger.debug("clear shiro缓存所有key，size：" + size);
        hashOperations().delete(hashKey);
    }

    @Override
    public int size() {
        Long size = hashOperations().size(hashKey);
        return size == null ? 0 : size.intValue();
    }

    @Override
    public Set<String> keys() {
        return hashOperations().keys(hashKey);
    }

    @Override
    public List<String> values() {
        return hashOperations().values(hashKey);
    }
}
