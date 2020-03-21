package group.zealot.king.base.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author zealot
 * @date 2020/3/21 9:42
 */
public abstract class BaseThreadPoolManager implements ZTThreadPoolManager {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final ZTThreadPool ztThreadPool;

    BaseThreadPoolManager() {
        ztThreadPool = new ZTThreadPool(getCoreSize(), getQueueSize());
    }

    public void execute(Runnable r) throws RejectedExecutionException {
        ztThreadPool.execute(r);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return ztThreadPool.submit(task);
    }


    protected int getCoreSize() {
        return 50;
    }

    protected int getQueueSize() {
        return 50;
    }
}
