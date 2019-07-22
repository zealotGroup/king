package group.zealot.king.demo.api.config;

import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.redis.RedisUtil;
import group.zealot.king.core.zt.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


public class RequestIdUtil {
    public static final String prefix = "api:requestId:";

    public static Duration timeout = Duration.ofMinutes(5);

    protected static Logger logger = LoggerFactory.getLogger(RequestIdUtil.class);

    public static boolean hasRequestId(String requestId) {
        return redisUtil().redisTemplate().delete(getKey(requestId));
    }

    public static String getRequestId() {
        String requestId = Funcation.createTime() + "" + Funcation.createRandom(6);
        if (redisUtil().setIfAbsent(getKey(requestId), true, timeout)) {
            return requestId;
        } else {
            return null;
        }
    }

    private static String getKey(String key) {
        return prefix + key;
    }

    private static RedisUtil redisUtil() {
        return SpringUtil.getApplicationContext().getBean(RedisUtil.class);
    }
}
