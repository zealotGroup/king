package group.zealot.king.demo.api.config;

import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.redis.RedisUtil;
import group.zealot.king.core.zt.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

public abstract class BaseLoginUtil {
    protected static Logger logger = LoggerFactory.getLogger(BaseLoginUtil.class);
    private static Duration timeout = Duration.ofMinutes(60);
    private static final String prefix = "api:token:";

    protected static String createToken(Object obj) {
        String token = UUID.randomUUID().toString();
        if (putSession(translatToKey(token), obj)) {
            ApiFilter.tokenLocal.set(token);
            return token;
        } else {
            throw new BaseRuntimeException("token创建异常，请稍后重试");
        }
    }

    public static void logout() {
        if (!removeSession()) {
            logger.warn("token已失效或logout异常");
        }
    }

    public static boolean isLogin() {
        return getSession() != null;
    }

    public static boolean flushExp() {
        return expireSession(timeout);
    }

    private static boolean removeSession() {
        return redisUtil().valueOperations().setIfPresent(getSessionKey(), null, Duration.ofSeconds(1));
    }

    protected static <T> T getSession() {
        return redisUtil().get(getSessionKey());
    }

    private static <T> boolean putSession(String key, T value) {
        return redisUtil().setIfAbsent(key, value, timeout);
    }


    private static boolean expireSession(Duration timeout) {
        String key = getSessionKey();
        return redisUtil().expire(key, timeout);
    }

    private static String getSessionKey() {
        return prefix + getToken();
    }

    private static String translatToKey(String token) {
        return prefix + token;
    }

    public static Duration getTimeout() {
        return timeout;
    }

    public static String getToken() {
        return ApiFilter.tokenLocal.get();
    }

    private static RedisUtil redisUtil() {
        return SpringUtil.getApplicationContext().getBean(RedisUtil.class);
    }
}
