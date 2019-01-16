package group.zealot.king.base.threadpool;

public class ServiceThread extends Thread {
	public ServiceThread(Runnable r) {
		super(r);
	}
}