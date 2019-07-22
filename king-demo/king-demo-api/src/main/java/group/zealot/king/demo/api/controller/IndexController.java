package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.core.zt.mif.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.mif.entity.system.SysRoute;
import group.zealot.king.demo.api.config.LoginUtil;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static group.zealot.king.core.zt.mif.Services.sysAuthService;
import static group.zealot.king.core.zt.mif.Services.sysRouteService;


@RestController
@RequestMapping("/")
public class IndexController {

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
    public JSONObject login(String username, byte[] password) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.NotNull(username, "用户名为空");
                Funcation.NotNull(password, "密码为空");
                String token = LoginUtil.login(username, password);
                JSONObject data = new JSONObject();
                data.put("token", token);
                data.put("timeout", LoginUtil.timeout.getSeconds());
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
                LoginUtil.logout();
            }
        }.result();
    }

    @RequestMapping("loginUserInfo")
    public JSONObject loginUserInfo() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {


                JSONObject data = new JSONObject();
                data.put("level", "super");
                data.put("routers", sysAuthService.getRoute(LoginUtil.getSysUserId()));
                resultJson.set(data);
            }
        }.result();
    }
}
