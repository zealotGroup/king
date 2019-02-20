package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.mybatis.system.entity.SysUser;
import group.zealot.king.core.zt.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class ResultFulSession {
    public static final Duration SESSIONID_TIMEOUT = Duration.ofMinutes(30L);
    /**
     * 返回给请求端的字段
     */
    public static final String SESSIONID_NAME = "sessionId";
    /**
     * 存在redis里的key前缀
     */
    private static final String REDIS_PREFIX_RESULTFUL_SESSION = "ResultFulSession:";
    @Autowired
    private RedisUtil redisUtil;


    private String createAndGetSessionId() {
        return Funcation.createRequestId();
    }

    public SysUser getSessionSysUser(String sessionId) {
        ValueOperations<String, String> valueOperations = redisUtil.valueOperations();
        String value = valueOperations.get(getKey(sessionId));
        if (value != null) {
            return JSONObject.parseObject(value, SysUser.class);
        }
        return null;
    }

    public String setSessionSysUser(SysUser sessionSysUser) {
        ValueOperations<String, String> valueOperations = redisUtil.valueOperations();
        String sessionId = createAndGetSessionId();
        Boolean fg =
                valueOperations.setIfAbsent(getKey(sessionId), JSONObject.toJSONString(sessionSysUser),
                        SESSIONID_TIMEOUT);
        if (fg) {
            return sessionId;
        }
        return null;
    }

    public Boolean updateSessionSysUserTimeout(String sessionId) {
        Boolean fg = redisUtil.redisTemplate().expire(getKey(sessionId), SESSIONID_TIMEOUT.getSeconds(), TimeUnit.SECONDS);
        return fg;
    }

    public Boolean delSessionSysUser(String sessionId) {
        Boolean fg = redisUtil.redisTemplate().delete(getKey(sessionId));
        return fg;
    }

    public String getSessionIdByRequest(HttpServletRequest request){
        return request.getHeader(SESSIONID_NAME);
    }
    private String getKey(String sessionId) {
        return REDIS_PREFIX_RESULTFUL_SESSION + sessionId;
    }

}
