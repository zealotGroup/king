package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.core.shiro.exception.ShiroException;
import group.zealot.king.core.shiro.realm.ShiroService;
import group.zealot.king.demo.api.config.ResultTemple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.concurrent.TimeUnit;

import static group.zealot.king.core.shiro.ShiroConfig.sessionTimeout;
import static group.zealot.king.core.shiro.sessionManager.ShiroSessionManager.SESSION_ID;


@RestController
@RequestMapping("/login")
public class LoginController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShiroService shiroService;

    @RequestMapping("/login")
    public JSONObject login(String username, String password) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                if (!shiroService.isAuthenticated()) {
                    try {
                        shiroService.login(username, password);
                    } catch (ShiroException e) {
                        logger.error("登录失败username=" + username, e);
                        resultJson.set(ServiceCode.REQUEST_ERROR);
                        return;
                    }
                }
                JSONObject data = new JSONObject();
                data.put(SESSION_ID, shiroService.getSessionId());
                data.put("token", shiroService.getPrincipal());
                data.put("timeout", sessionTimeout);
                data.put("unit", TimeUnit.SECONDS);
                data.put("info", "连续请求可续约");
                resultJson.set(data);
                resultJson.set(ServiceCode.SUCCESS);
            }
        }.result();
    }

    @RequestMapping("/logout")
    public JSONObject logout() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                if (shiroService.isAuthenticated()) {
                    shiroService.logout();
                }
                JSONObject data = new JSONObject();
                data.put("msg", "登录已注销");
                resultJson.set(data);
                resultJson.set(ServiceCode.SUCCESS);
            }
        }.result();
    }
}
