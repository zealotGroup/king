package group.zealot.king.base;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

public class Funcation {
    public static final int CURRENT_METHOD = 0;
    public static final int PREVIOUS_METHOD = 1;//0 为本方法名称 ， 1 为上一个调用

    public static String getHostName() {
        try {
            return getInetAddress().getHostName();
        } catch (UnknownHostException e) {
            return "UnknownHostException";
        }
    }

    public static String getHostAddress() {
        try {
            return getInetAddress().getHostAddress();
        } catch (UnknownHostException e) {
            return "UnknownHostException";
        }
    }

    public static InetAddress getInetAddress() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    public static StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    public static String getThisMethodName() {
        return getStackTrace()[PREVIOUS_METHOD].getMethodName();
    }

    /**
     * 毫秒级别
     */
    public static long createTime() {
        return Instant.now().toEpochMilli();
    }

    public static long createRandom(int size) {
        return ((Double) (Math.random() * Math.pow(10, size))).longValue();
    }
}
