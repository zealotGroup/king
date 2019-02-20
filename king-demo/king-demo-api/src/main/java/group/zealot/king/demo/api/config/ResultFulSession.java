package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.mybatis.system.entity.SysUser;
import group.zealot.king.core.zt.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

import static group.zealot.king.demo.api.config.RequestFilter.TOKEN_NAME;

@Component
public class ResultFulSession {
    private final String RESULTFUL_SESSION = "ResultFulSession:";
    @Autowired
    private RedisUtil redisUtil;

    public String createAndGetSessionId() {
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
                        30 * 60, TimeUnit.SECONDS);
        if (fg) {
            return sessionId;
        }
        return null;
    }

    public Boolean delSessionSysUser(String sessionId) {
        Boolean fg = redisUtil.redisTemplate().delete(getKey(sessionId));
        return fg;
    }

    public Boolean notFoundSessionSysUser(String sessionId) {
        return redisUtil.redisTemplate().opsForValue().get(getKey(sessionId)) == null;
    }

    private String getKey(String sessionId) {
        return RESULTFUL_SESSION + sessionId;
    }

    public String getTokenBySessionId(String sessionId) {
        return Funcation.getRandom(1) + sessionId;
    }

    public String getSessionIdByToken(String token) {
        return token.substring(1);
    }

    public String getToken(HttpServletRequest request){
        return request.getHeader(TOKEN_NAME);
    }
}
