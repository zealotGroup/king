package group.zealot.king.demo.api.controller.oauth;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.demo.api.config.BaseLoginUtil;
import group.zealot.king.demo.api.config.LoginUtil;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.sysAuthService;


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

    @RequestMapping("checkToken")
    public JSONObject checkToken() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                JSONObject data = new JSONObject();
                data.put("timeout", !LoginUtil.isLogin());
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("login")
    public JSONObject login(@ZTValid(NotBlank = true) String username, @ZTValid(NotEmpty = true) byte[] password) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                String token = LoginUtil.login(username, password);
                JSONObject data = new JSONObject();
                data.put("token", token);
                data.put("timeout", BaseLoginUtil.getTimeout().getSeconds());
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
                data.put("level", LoginUtil.getSysUser().getLevel());
                data.put("routes", sysAuthService.getRoute(LoginUtil.getSysUser().getId()));
                resultJson.set(data);
            }
        }.result();
    }
}
