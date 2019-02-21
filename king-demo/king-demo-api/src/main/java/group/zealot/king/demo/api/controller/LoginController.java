package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.mybatis.system.entity.SysUser;
import group.zealot.king.core.zt.mybatis.system.service.SysUserService;
import group.zealot.king.demo.api.config.ResultFul;
import group.zealot.king.demo.api.config.ResultFulSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.concurrent.TimeUnit;

import static group.zealot.king.demo.api.config.ResultFulSession.SESSIONID_NAME;
import static group.zealot.king.demo.api.config.ResultFulSession.SESSIONID_TIMEOUT;


@RestController
@RequestMapping("/login")
public class LoginController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ResultFulSession resultFulSession;

    @RequestMapping("/login")
    public JSONObject login(String username, String password) {
        return new ResultFul() {
            @Override
            protected void dosomething() {
                SysUser sysUser = sysUserService.getByUsernameAndPassword(username, password);
                if (sysUser == null) {
                    resultJson.set(ServiceCode.REQUEST_ERROR);
                } else {
                    int size = 5;
                    do {
                        String sessionId = resultFulSession.setSessionSysUser(sysUser);
                        if (sessionId != null) {
                            JSONObject data = new JSONObject();
                            data.put(SESSIONID_NAME, sessionId);
                            data.put("timeout", SESSIONID_TIMEOUT.getSeconds());
                            data.put("unit", TimeUnit.SECONDS);
                            data.put("info", "连续请求可续约");
                            resultJson.set(data);
                            resultJson.set(ServiceCode.SUCCESS).put(SESSIONID_NAME, sessionId);
                            break;
                        } else {
                            size--;
                        }
                    } while (size > 0);
                    if (size <= 0) {
                        throw new BaseRuntimeException("服务出现异常，请稍候再试");
                    }
                }
            }
        }.result();
    }

    @RequestMapping("/logout")
    public JSONObject logout(HttpServletRequest request) {
        return new ResultFul() {
            @Override
            protected void dosomething() {
                String sessionId = resultFulSession.getSessionIdByRequest(request);
                int size = 5;
                do {
                    if (resultFulSession.delSessionSysUser(sessionId)) {
                        resultJson.set(ServiceCode.SUCCESS);
                        break;
                    } else {
                        if (resultFulSession.getSessionSysUser(sessionId) == null) {
                            break;
                        }
                        size--;
                    }
                } while (size > 0);
                if (size <= 0) {
                    throw new BaseRuntimeException("服务出现异常，请稍候再试");
                }

            }
        }.result();
    }
}
