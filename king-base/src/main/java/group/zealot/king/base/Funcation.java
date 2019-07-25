package group.zealot.king.base;

import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.util.StringUtil;
import org.junit.Assert;

import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;

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

    /**
     * 创建 size 位的随机数
     */
    public static long createRandom(int size) {
        return ((Double) (Math.random() * Math.pow(10, size))).longValue();
    }

    public static void IsNull(Object value, String message) {
        try {
            Assert.assertNull(message, value);
        } catch (AssertionError e) {
            throw new BaseRuntimeException(e.getMessage());
        }
    }

    public static void NotNull(Object value, String message) {
        try {
            Assert.assertNotNull(message, value);
        } catch (AssertionError e) {
            throw new BaseRuntimeException(e.getMessage());
        }
    }

    public static void NotNull(Object value, String message, ServiceCode serviceCode) {
        try {
            Assert.assertNotNull(message, value);
        } catch (AssertionError e) {
            throw new BaseRuntimeException(serviceCode);
        }
    }
}
