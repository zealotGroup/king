package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.core.shiro.ShiroConfig;
import group.zealot.king.core.shiro.realm.ShiroService;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {
    @Autowired
    ShiroService shiroService;

    @RequestMapping
    public JSONObject index() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                JSONObject data = new JSONObject();
                data.put("status", "API服务状态正常");
                resultJson.set(data);
                resultJson.set(ServiceCode.SUCCESS);
            }
        }.result();
    }

    @RequestMapping("login")
    public JSONObject login(String username, String password) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                if (!shiroService.isAuthenticated()) {
                    shiroService.login(username, password);
                }
                JSONObject data = new JSONObject();
                data.put("sessionId", shiroService.getSessionId());
                data.put("timeout", ShiroConfig.sessionTimeout / 1000);
                data.put("unit", "SECONDS");
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("logout")
    public JSONObject logout() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                if (shiroService.isAuthenticated()) {
                    shiroService.logout();
                }
            }
        }.result();
    }

    @RequestMapping("loginInfo")
    public JSONObject loginInfo() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                JSONObject data = new JSONObject();
                data.put("level", "API服务状态正常");
                JSONArray routers = new JSONArray();//根据登录用户 获取菜单
                data.put("routers", routers);
                resultJson.setData(data);
            }
        }.result();
    }
}
