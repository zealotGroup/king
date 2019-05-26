package group.zealot.king.core.db.mybatis.cache;

import group.zealot.king.base.exception.BaseRuntimeException;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class CacheAbs implements Cache {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 此实例cache ID
     */
    private final String id;
    private final ReadWriteLock readWriteLock;

    protected CacheAbs(String id) {
        if (id == null) {
            throw new BaseRuntimeException("Cache instances require an ID");
        }
        this.id = id;
        this.readWriteLock = new ReentrantReadWriteLock();
    }

    /**
     * 返回此次缓存的key
     * k 非null
     */
    protected abstract String getK(Object k);

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (key != null) {
            doPut(getK(key), value);
        } else {
            logger.warn("key is null");
        }
    }

    /**
     * doPut(Object k, Object value)
     * k 为调用getK(key) 后的k
     */
    protected abstract void doPut(String k, Object value);

    @Override
    public Object getObject(Object key) {
        if (key != null) {
            return doGet(getK(key));
        } else {
            logger.warn("key is null");
        }
        return null;
    }

    /**
     * doGet(Object k)
     * k 为调用getK(key) 后的k
     */
    protected abstract Object doGet(String k);

    @Override
    public Object removeObject(Object key) {
        if (key != null) {
            doRemoveObject(getK(key));
        } else {
            logger.warn("key is null");
        }
        return null;
    }

    /**
     * doRemoveObject(Object k)
     * k 为调用getK(key) 后的k
     */
    protected abstract Object doRemoveObject(String k);

    @Override
    public void clear() {
        logger.warn("mybatis clear cache id: " + getId());
        doClear();
        logger.warn("mybatis clear cache id: " + getId() + " success");
    }

    protected abstract Long doClear();

    @Override
    public int getSize() {
        int size = doGetSize().intValue();
        logger.info("mybatis cache id: " + getId() + " size : " + size);
        return size;
    }

    protected abstract Long doGetSize();

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
