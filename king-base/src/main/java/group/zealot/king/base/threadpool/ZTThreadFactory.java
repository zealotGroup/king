package group.zealot.king.base.threadpool;

import java.util.concurrent.ThreadFactory;

public class ZTThreadFactory implements ThreadFactory {

	@Override
    public ZTThread newThread(Runnable r) {
        return new ZTThread(r);
	}
}
