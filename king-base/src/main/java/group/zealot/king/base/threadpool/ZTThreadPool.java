package group.zealot.king.base.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ZTThreadPool extends ThreadPoolExecutor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 允许线程空闲回收
     * 临时创建线程数为1
     * 临时线程超时时间1
     *
     * @param poolSize       执行的线程
     * @param poolBufferSize 队列长度
     */
    public ZTThreadPool(int poolSize, int poolBufferSize) {
        this(poolSize, 1, 1, poolBufferSize);
    }

    public ZTThreadPool(int corePoolSize, int maximumPoolSize, int keepAliveTime, int poolBufferSize) {
        this(corePoolSize, maximumPoolSize, true, keepAliveTime, poolBufferSize);
    }

    /**
     * @param corePoolSize           执行的线程
     * @param maximumPoolSize        如果队列满了，线程池也满了，允许临时创建的线程数
     * @param allowCoreThreadTimeOut 线程池线程在空闲时，是否允许回收
     * @param keepAliveTime          临时线程超时时间
     * @param poolBufferSize         队列长度
     */
    public ZTThreadPool(int corePoolSize, int maximumPoolSize, boolean allowCoreThreadTimeOut, int keepAliveTime, int poolBufferSize) {
        super(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(poolBufferSize),
                new ZTThreadFactory(),
                new AbortPolicy());
        this.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
        this.prestartAllCoreThreads();
    }
}
