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
     * 时间+6位随机
     * 1550563588237
     */
    public static String createRequestId() {
        Instant s = Instant.now();
        return s.toEpochMilli() + "" + getRandom(6);
    }

    public static long getRandom(int size) {
        return ((Double) (Math.random() * Math.pow(10, size))).longValue();
    }

    public static void main(String[] args) {
        System.out.println(createRequestId());
    }
}
