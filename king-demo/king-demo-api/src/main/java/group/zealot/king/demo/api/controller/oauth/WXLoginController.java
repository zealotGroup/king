package group.zealot.king.demo.api.controller.oauth;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.util.EnvironmentUtil;
import group.zealot.king.core.shiro.LoginUtil;
import group.zealot.king.core.shiro.realms.ShiroToken;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.jxc.JxcCust;
import group.zealot.king.demo.api.config.ResultTemple;
import group.zealot.king.demo.api.controller.wx.WXAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.jxcCustService;

/**
 * @author zealot
 * @date 2020/2/29 17:28
 */
@RestController
@RequestMapping("/oauth/wx")
public class WXLoginController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WXAPI wxapi;

    @RequestMapping("login")
    @Validated
    public JSONObject login(@ZTValid(NotBlank = true) String code) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                if (!LoginUtil.isLogin()) {
                    logger.info(code);
                    JSONObject jscode2session = wxapi.code2Session(code);
                    ShiroToken token = new ShiroToken(jscode2session.getString("openid"), jscode2session.getString("session_key"));
                    LoginUtil.login(token);
                }
                resultJson.set(getToken());
            }
        }.result();
    }

    @RequestMapping("register")
    @Validated
    public JSONObject register(@ZTValid(NotBlank = true) String code, @ZTValid(NotBlank = true) String encryptedData, @ZTValid(NotBlank = true) String iv) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                logger.info(code);
                JSONObject jscode2session = wxapi.code2Session(code);
                JSONObject userInfo = wxapi.decrypt(encryptedData, jscode2session.getString("session_key"), iv);

                if (!jscode2session.getString("openid").equals(userInfo.getString("openId")) ||
                        !EnvironmentUtil.get("wx.appid").equals(userInfo.getJSONObject("watermark").getString("appid"))
                ) {
                    throw new BaseRuntimeException(ServiceCode.PARAM_IS_INVALID, "数据校验失败");
                }
                JxcCust jxcCust = new JxcCust();
                jxcCust.setOpenid(userInfo.getString("openId"));

                jxcCust = jxcCustService.get(jxcCust);
                if (jxcCust == null) {
                    jxcCust = new JxcCust();
                    jxcCust.setOpenid(userInfo.getString("openId"));
                    jxcCust.setAvatarUrl(userInfo.getString("avatarUrl"));
                    jxcCust.setNickName(userInfo.getString("nickName"));
                    jxcCustService.insert(jxcCust);
                } else {
                    jxcCust.setAvatarUrl(userInfo.getString("avatarUrl"));
                    jxcCust.setNickName(userInfo.getString("nickName"));
                    jxcCustService.update(jxcCust);
                }
                //自动登录
                ShiroToken token = new ShiroToken(jscode2session.getString("openid"), jscode2session.getString("session_key"));
                LoginUtil.login(token);
                resultJson.set(getToken());
            }
        }.result();
    }

    private JSONObject getToken() {
        JSONObject data = new JSONObject();
        data.put("user", LoginUtil.getUser());
        data.put("token", LoginUtil.getSession().getId());
        data.put("timeout", LoginUtil.getSession().getTimeout());
        data.put("unit", "MILLISECONDS");
        return data;
    }
}
