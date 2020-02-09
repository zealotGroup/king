package group.zealot.king.core.db.mybatis.core.cache;

import group.zealot.king.core.zt.redis.RedisUtil;
import group.zealot.king.core.zt.spring.SpringUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;

import java.time.Duration;
import java.util.List;

/**
 * redis作mybatis的二级缓存
 * 特点：
 * 1、利用hash存储key，value放置在string中（有超时）
 */
public class RedisStringCache extends CacheAbs {
    /**
     * redis key 前缀
     */
    private static final String keyPref = "cache:mybatis:string";
    private static final String keyPrefH = "cache:mybatis:stringH";

    private final String K;
    private final String KH;

    private final Duration timeout;

    public RedisStringCache(final String id) {
        super(id);
        this.K = keyPref + ":" + id;
        this.KH = keyPrefH + ":" + id;
        this.timeout = Duration.ofSeconds(60L);
    }

    private RedisTemplate<String, Object> redisTemplate() {
        RedisUtil redisUtil = SpringUtil.getApplicationContext().getBean(RedisUtil.class);
        return redisUtil.redisTemplate();
    }

    private ValueOperations<String, Object> getValueOperations() {
        RedisUtil redisUtil = SpringUtil.getApplicationContext().getBean(RedisUtil.class);
        return redisUtil.valueOperations();
    }

    private HashOperations<String, String, Object> getHashOperations() {
        RedisUtil redisUtil = SpringUtil.getApplicationContext().getBean(RedisUtil.class);
        return redisUtil.hashOperations();
    }

    private String getKH() {
        return KH;
    }

    @Override
    protected String getK(Object key) {
        return K + ":" + key.toString();
    }

    @Override
    protected void doPut(String key, Object value) {
        logger.debug("mybatis cache [doPut] " + "key[" + key + "]");
        redisTemplate().execute(
                new SessionCallback<List<Object>>() {
                    @Override
                    public List<Object> execute(RedisOperations operations) throws DataAccessException {
                        operations.multi();
                        operations.opsForValue().set(key, value, timeout);
                        operations.opsForHash().put(getKH(), key, true);
                        return operations.exec();
                    }
                }
        );
    }

    @Override
    protected Object doGet(String key) {
        logger.debug("mybatis cache [doGet] " + "key[" + key + "]");
        if (redisTemplate().hasKey(getKH()) && getHashOperations().hasKey(getKH(), key)) {
            return getValueOperations().get(key);
        }
        return null;
    }

    @Override
    protected Object doRemoveObject(String key) {
        logger.debug("mybatis cache [doRemoveObject] " + "key[" + key + "] [expire]");
        redisTemplate().opsForHash().delete(getKH(), key);
        return null;
    }

    @Override
    protected Long doClear() {
        logger.warn("mybatis cache [doClear] ");
        redisTemplate().delete(getKH());
        return null;
    }

    @Override
    protected Long doGetSize() {
        logger.info("mybatis cache [doGetSize] ");
        return getHashOperations().size(getKH());
    }
}
