package group.zealot.king.core.shiro.cache;

import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.zt.redis.RedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

public class ShiroRedisCache implements Cache<String, String> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RedisUtil redisUtil;
    private final String keyPrefix = "shiro:sessionid:";
    private final int expire = 30 * 24 * 60 * 60 * 1000;

    ShiroRedisCache(RedisUtil redisUtil) {
        logger.debug("创建ShiroRedisCache");
        this.redisUtil = redisUtil;
    }

    @Override
    public String get(String key) throws CacheException {
        logger.debug("get shiro缓存key：" + key);
        return redisUtil.get(key);
    }

    @Override
    public String put(String key, String value) throws CacheException {
        logger.debug("put shiro缓存key：" + key);
        String old = redisUtil.get(key);
        redisUtil.set(key, value);
        return old;
    }

    @Override
    public String remove(String key) throws CacheException {
        logger.debug("delete shiro缓存key：" + key);
        String old = redisUtil.get(key);
        redisUtil.delete(key);
        return old;
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("clear shiro缓存所有key");
        redisUtil.delete(keyPrefix + "*");
    }

    @Override
    public int size() {
        return redisUtil.keys(keyPrefix);
    }

    @Override
    public Set<String> keys() {
        return null;
    }

    @Override
    public Collection<String> values() {
        return null;
    }
}
