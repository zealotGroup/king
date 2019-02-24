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
@RequestMapping("/user")
public class UserController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShiroService shiroService;

    @RequestMapping("/info")
    public JSONObject login() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                JSONObject data = new JSONObject();
                data.put("name", shiroService.getPrincipal().getUsername());
                data.put("principals", shiroService.getSimpleAuthorizationInfo());
                resultJson.set(data);
                resultJson.set(ServiceCode.SUCCESS);
            }
        }.result();
    }
}
