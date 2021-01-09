package group.zealot.king.base.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

public interface ZTThreadPoolManager {

    void execute(Runnable r) throws RejectedExecutionException;

    <T> Future<T> submit(Callable<T> task);
}
