package group.zealot.king.core.shiro.cache;

import group.zealot.king.core.zt.redis.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class ShiroRedisCache implements Cache<Serializable, Session> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private RedisUtil redisUtil;
    private String hashKey;

    public ShiroRedisCache(RedisUtil redisUtil, String hashKey) {
        this.redisUtil = redisUtil;
        this.hashKey = hashKey;
    }

    private HashOperations<String, Serializable, Session> hashOperations() {
        return redisUtil.hashOperations();
    }

    @Override
    public Session get(Serializable key) throws CacheException {
        logger.debug("get " + hashKey + "缓存key：" + key);
        return hashOperations().get(hashKey, key);
    }

    @Override
    public Session put(Serializable key, Session value) throws CacheException {
        logger.debug("put " + hashKey + "缓存key：" + key + "，value：" + value);
        Session old = get(key);
        hashOperations().put(hashKey, key, value);
        return old;
    }

    @Override
    public Session remove(Serializable key) throws CacheException {
        logger.debug("delete " + hashKey + "缓存key：" + key);
        Session old = get(key);
        hashOperations().delete(hashKey, key);
        return old;
    }

    @Override
    public void clear() throws CacheException {
        Long size = hashOperations().size(hashKey);
        logger.debug("clear " + hashKey + "缓存所有key，size：" + size);
        hashOperations().delete(hashKey);
    }

    @Override
    public int size() {
        Long size = hashOperations().size(hashKey);
        logger.debug("size " + hashKey + "缓存所有key，size：" + size);
        return size == null ? 0 : size.intValue();
    }

    @Override
    public Set<Serializable> keys() {
        return hashOperations().keys(hashKey);
    }

    @Override
    public List<Session> values() {
        return hashOperations().values(hashKey);
    }
}
