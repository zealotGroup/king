package group.zealot.king.demo.api.config;

import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.security.DigestUtils;
import group.zealot.king.base.util.EncodeUtil;
import group.zealot.king.core.zt.mif.entity.system.SysUser;
import group.zealot.king.core.zt.redis.RedisUtil;
import group.zealot.king.core.zt.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static group.zealot.king.core.zt.mif.Services.*;

public class LoginUtil {
    private static final String prefix = "api:token:";
    protected static final ThreadLocal<HttpServletRequest> threadLocalRequest = new ThreadLocal<>();

    public static Duration timeout = Duration.ofMinutes(30);

    protected static Logger logger = LoggerFactory.getLogger(LoginUtil.class);

    public static String login(String username, byte[] password) {
        //加密密码
        byte[] hashPassword = DigestUtils.sha1(password, username.getBytes(), 1024);
        String newPassword = EncodeUtil.encodeHex(hashPassword);

        SysUser sysUser = sysUserService.getByUsernameAndPassword(username, newPassword);
        if (sysUser == null) {
            throw new BaseRuntimeException("用户名或密码错误");
        } else {
            String token = UUID.randomUUID().toString();
            if (put(token, sysUser)) {
                return token;
            } else {
                throw new BaseRuntimeException("token创建异常，请稍后重试");
            }
        }
    }

    public static void logout() {
        if (!remove(getToken())) {
            throw new BaseRuntimeException("token已失效或logout异常");
        }
    }

    public static boolean isLogin(){
        return get(getToken()) != null;
    }

    public static SysUser getSysUser() {
        Object value = get(getToken());
        if (value instanceof SysUser) {
            return (SysUser) value;
        } else {
            throw new BaseRuntimeException("token已失效或logout异常");
        }
    }

    public static Long getSysUserId() {
       return getSysUser().getId();
    }

    public static String getToken() {
        String token = threadLocalRequest.get().getHeader(ApiFilter.token_header);
        return token;
    }

    public static boolean flushExp() {
        return expire(getToken());
    }

    private static boolean remove(String key) {
        key = getKey(key);
        logger.info("logout redis remove(setIfPresent timeout 1s) :" + key);
        return redisUtil().valueOperations().setIfPresent(key, null, Duration.ofSeconds(1));
    }

    private static boolean put(String key, Object value) {
        key = getKey(key);
        return redisUtil().setIfAbsent(key, value, timeout);
    }

    private static Object get(String key) {
        key = getKey(key);
        return redisUtil().get(key);
    }

    private static boolean expire(String key) {
        key = getKey(key);
        return redisUtil().expire(key, timeout);
    }

    private static String getKey(String key) {
        return prefix + key;
    }

    private static RedisUtil redisUtil() {
        return SpringUtil.getApplicationContext().getBean(RedisUtil.class);
    }

}
