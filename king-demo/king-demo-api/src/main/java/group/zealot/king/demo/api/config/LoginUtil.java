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

    public static void logout(HttpServletRequest request) {
        String token = request.getHeader(ApiFilter.token_header);
        if (!remove(token)) {
            throw new BaseRuntimeException("token已失效或logout异常");
        }
    }

    public static SysUser getSysUser(HttpServletRequest request) {
        String token = request.getHeader(ApiFilter.token_header);
        Object value = get(token);
        if (value instanceof SysUser) {
            return (SysUser) value;
        } else {
            return null;
        }
    }

    public static boolean flushExp(HttpServletRequest request) {
        String token = request.getHeader(ApiFilter.token_header);
        return expire(token);
    }

    private static boolean remove(String key) {
        key = prefix + key;
        logger.info("logout redis remove :" + key);
        return redisUtil().valueOperations().setIfPresent(key, null, Duration.ofSeconds(1));
    }

    private static boolean put(String key, Object value) {
        key = prefix + key;
        logger.info("login redis put :" + key);
        return redisUtil().valueOperations().setIfAbsent(key, value, timeout);
    }

    private static Object get(String key) {
        key = prefix + key;
        return redisUtil().valueOperations().get(key);
    }

    private static boolean expire(String key) {
        key = prefix + key;
        return redisUtil().redisTemplate().expire(key, timeout.getSeconds(), TimeUnit.SECONDS);
    }

    private static RedisUtil redisUtil() {
        return SpringUtil.getApplicationContext().getBean(RedisUtil.class);
    }

}
