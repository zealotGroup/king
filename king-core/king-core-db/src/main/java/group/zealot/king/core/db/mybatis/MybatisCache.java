package group.zealot.king.core.db.mybatis;

import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.redis.RedisUtil;
import group.zealot.king.core.zt.spring.SpringUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * redis作mybatis的二级缓存
 * 特点：
 * 1、hash存储-》无超时
 */
public class MybatisCache implements Cache {
    /**
     * redis key 前缀
     */
    public static final String hashKeyPref = "cache:mybatis";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 此实例cache ID
     */
    private final String id;
    private final HashOperations<String, String, Object> hashOperations;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final String K;

    public MybatisCache(final String id) {
        if (id == null) {
            throw new BaseRuntimeException("Cache instances require an ID");
        }
        this.id = id;
        this.K = hashKeyPref + ":" + id;
        RedisUtil redisUtil = SpringUtil.getApplicationContext().getBean(RedisUtil.class);
        hashOperations = redisUtil.hashOperations();
        logger.info("MybatisCache 初始化");
    }

    /**
     * 返回此hash的K
     */
    private String getK() {
        return K;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        logger.debug("mybatis cache [putObject] K: " + getK() + " HK: " + key);
        if (key != null) {
            hashOperations.put(getK(), key.toString(), value);
        } else {
            logger.warn("key is null");
        }
    }

    @Override
    public Object getObject(Object key) {
        logger.debug("mybatis cache [getObject] K: " + getK() + " HK: " + key);
        if (key != null) {
            return hashOperations.get(getK(), key.toString());
        } else {
            logger.warn("key is null");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        logger.debug("mybatis cache [removeObject] K: " + getK() + " HK: " + key);
        if (key != null) {
            hashOperations.delete(getK(), key.toString());
        } else {
            logger.warn("key is null");
        }
        return null;
    }

    @Override
    public void clear() {
        logger.warn("clear cache : " + getK());
        hashOperations.delete(getK());
        logger.warn("clear cache : " + getK() + " success");
    }

    @Override
    public int getSize() {
        int size = hashOperations.size(getK()).intValue();
        logger.info("cache K : " + getK() + " size : " + size);
        return size;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
