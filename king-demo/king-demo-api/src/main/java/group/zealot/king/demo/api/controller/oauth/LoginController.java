package group.zealot.king.demo.api.controller.oauth;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.shiro.LoginUtil;
import group.zealot.king.core.shiro.realms.ShiroToken;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.system.SysUser;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.sysAuthService;


@RestController
@RequestMapping("/oauth")
public class LoginController {

    @RequestMapping("login")
    public JSONObject login(@ZTValid(NotBlank = true) String username, @ZTValid(NotEmpty = true) byte[] password) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                if (!LoginUtil.isLogin()) {
                    ShiroToken token = new ShiroToken(username, password);
                    LoginUtil.login(token);
                }

                JSONObject data = new JSONObject();
                data.put("auth_session_id", LoginUtil.getSession().getId());
                data.put("timeout", LoginUtil.getSession().getTimeout());
                data.put("unit", "MILLISECONDS");
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("logout")
    public JSONObject logout() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                if (LoginUtil.isLogin()) {
                    LoginUtil.logout();
                }

            }
        }.result();
    }

    @RequestMapping("userInfo")
    public JSONObject userInfo() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                SysUser sysUser = LoginUtil.getUser();

                JSONObject data = new JSONObject();
                data.put("level", sysUser.getLevel());
                data.put("routes", sysAuthService.getRoute(sysUser.getId()));
                resultJson.set(data);
            }
        }.result();
    }
}
