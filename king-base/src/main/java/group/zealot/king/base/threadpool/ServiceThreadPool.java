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
	
	public ServiceThreadPool(int corePoolSize, int maximumPoolSize,int poolSelfMaintenTime,int poolBufferSize){
		super(  corePoolSize,
				maximumPoolSize,
				poolSelfMaintenTime,
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(poolBufferSize),
				new ServiceThreadFactory(),
				new AbortPolicy() );
		this.allowCoreThreadTimeOut(true);
		this.prestartAllCoreThreads();
	}
		
	protected void beforeExecute(Thread t,Runnable r){
		super.beforeExecute(t,r);

		pauseLock.lock();
	    try {
	    	while (isPaused) unpaused.await();
	    } 
	    catch(InterruptedException e) {
	    }
	    finally {
	    	pauseLock.unlock();
	    	counter.incrementAndGet();
	    }
	}
	
	protected void afterExecute(Runnable r,Throwable t){
		super.afterExecute(r,t);
		counter.decrementAndGet();
	}
	

	public void pauseService() {
	    pauseLock.lock();
	    try {
	    	isPaused = true;
	    	while(counter.get()>0){
	    		//
	    	}
	    } 
	    finally {
	    	pauseLock.unlock();
	    }
	}
	
	public void resumeService() {
	    pauseLock.lock();
	    try {
	    	isPaused = false;
	    	unpaused.signalAll();
	    }
	    finally {
	    	pauseLock.unlock();
	    }
	}
}
