package group.zealot.king.core.db.mybatis.cache;

import group.zealot.king.core.zt.redis.RedisUtil;
import group.zealot.king.core.zt.spring.SpringUtil;
import org.springframework.data.redis.core.HashOperations;

/**
 * redis作mybatis的二级缓存
 * 特点：
 * 1、hash存储-》无超时
 */
public class RedisHashCache extends CacheAbs {
    /**
     * redis key 前缀
     */
    private static final String keyPref = "cache:mybatis:hash";

    private final String K;

    public RedisHashCache(final String id) {
        super(id);
        this.K = keyPref + ":" + id;
    }

    private HashOperations<String, String, Object> getOperations() {
        RedisUtil redisUtil = SpringUtil.getApplicationContext().getBean(RedisUtil.class);
        return redisUtil.hashOperations();
    }

    /**
     * 返回此hash的K
     */
    private String getHashKey() {
        return K;
    }

    @Override
    protected String getK(Object key) {
        return key.toString();
    }

    @Override
    protected void doPut(String key, Object value) {
        logger.debug("mybatis cache [doPut] hashK[" + getHashKey() + "]" + "key[" + key + "]");
        getOperations().put(getHashKey(), key, value);
    }

    @Override
    protected Object doGet(String key) {
        logger.debug("mybatis cache [doGet] hashK[" + getHashKey() + "]" + "key[" + key + "]");
        return getOperations().get(getHashKey(), key);
    }

    @Override
    protected Object doRemoveObject(String key) {
        logger.debug("mybatis cache [doRemoveObject] hashK[" + getHashKey() + "]" + "key[" + key + "]");
        return getOperations().delete(getHashKey(), key);
    }

    @Override
    protected Long doClear() {
        logger.warn("mybatis cache [doClear] hashK[" + getHashKey() + "]");
        return getOperations().delete(getHashKey());
    }

    @Override
    protected Long doGetSize() {
        logger.info("mybatis cache [doGetSize] hashK[" + getHashKey() + "]");
        return getOperations().size(getHashKey());
    }

}
