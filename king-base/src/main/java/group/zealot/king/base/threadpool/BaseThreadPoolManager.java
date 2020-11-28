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
public class BaseThreadPoolManager implements ZTThreadPoolManager {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static ZTThreadPool ztThreadPool;

    private BaseThreadPoolManager() {
        int core = getCoreSize();
        int queue = getQueueSize();
        logger.debug("create thread pool: core {} queue {}", core, queue);
        ztThreadPool = new ZTThreadPool(core, queue);
    }

    public static BaseThreadPoolManager getInstance() {
        return FG.manager;
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

    private static class FG {
        private final static BaseThreadPoolManager manager = new BaseThreadPoolManager();
    }
}
