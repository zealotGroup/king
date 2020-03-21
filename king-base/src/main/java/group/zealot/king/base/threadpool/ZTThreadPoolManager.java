package group.zealot.king.base.threadpool;

import java.util.concurrent.*;

public interface ZTThreadPoolManager {

    void execute(Runnable r) throws RejectedExecutionException;

    <T> Future<T> submit(Callable<T> task);
}
