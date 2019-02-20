package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
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

import static group.zealot.king.demo.api.config.ResultFulSession.SESSIONID_NAME;


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
                            resultJson.set(ServiceCode.SUCCESS).put(SESSIONID_NAME, sessionId);
                            break;
                        } else {
                            size--;
                        }
                    } while (size > 0);
                    if (size <= 0) {
                        resultJson.set(ServiceCode.EXCEPTION_RUNNTIME);
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
                    resultJson.set(ServiceCode.EXCEPTION_RUNNTIME);
                }

            }
        }.result();
    }
}
