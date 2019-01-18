package group.zealot.king.base.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ServiceThreadPool extends ThreadPoolExecutor {

    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();
    private AtomicLong counter = new AtomicLong();

    /**
     * 允许线程空闲回收
     * 临时创建线程数为1
     * 临时线程超时时间0
     */
    public ServiceThreadPool(int poolSize, int poolBufferSize) {
        this(poolSize, 1, 0, poolBufferSize);
    }

    public ServiceThreadPool(int corePoolSize, int maximumPoolSize, int keepAliveTime, int poolBufferSize) {
        this(corePoolSize, maximumPoolSize, true, keepAliveTime, poolBufferSize);
    }

    /**
     * @param corePoolSize           执行的线程
     * @param maximumPoolSize        如果队列满了，线程池也满了，允许临时创建的线程数
     * @param allowCoreThreadTimeOut 线程池线程在空闲时，是否允许回收
     * @param keepAliveTime          临时线程超时时间
     * @param poolBufferSize         队列长度
     */
    public ServiceThreadPool(int corePoolSize, int maximumPoolSize, boolean allowCoreThreadTimeOut, int keepAliveTime, int poolBufferSize) {
        super(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(poolBufferSize),
                new ServiceThreadFactory(),
                new AbortPolicy());
        this.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
        this.prestartAllCoreThreads();
    }

    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);

        pauseLock.lock();
        try {
            while (isPaused) unpaused.await();
        } catch (InterruptedException e) {
        } finally {
            pauseLock.unlock();
            counter.incrementAndGet();
        }
    }

    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        counter.decrementAndGet();
    }


    public void pauseService() {
        pauseLock.lock();
        try {
            isPaused = true;
            while (counter.get() > 0) {
                //
            }
        } finally {
            pauseLock.unlock();
        }
    }

    public void resumeService() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }
}
