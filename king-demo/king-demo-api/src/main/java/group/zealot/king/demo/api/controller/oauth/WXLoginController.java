package group.zealot.king.demo.api.controller.oauth;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.security.CryptoUtils;
import group.zealot.king.base.util.EnvironmentUtil;
import group.zealot.king.core.zt.entity.jxc.JxcCust;
import group.zealot.king.demo.api.config.LoginUtil;
import group.zealot.king.demo.api.config.ResultTemple;
import group.zealot.king.demo.api.config.WxLoginUtil;
import group.zealot.king.demo.api.controller.wx.WXAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.Base64;

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
    public JSONObject login(@NotEmpty String code) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                JSONObject data = new JSONObject();
                if (!WxLoginUtil.isLogin()) {
                    logger.info(code);
                    JSONObject jscode2session = wxapi.code2Session(code);
                    WxLoginUtil.login(jscode2session.getString("openid"));
                }
                data.put("user", WxLoginUtil.getJxcCust());
                data.put("token", WxLoginUtil.getToken());
                data.put("timeout", WxLoginUtil.getTimeout().getSeconds());
                data.put("unit", "SECONDS");
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("register")
    @Validated
    public JSONObject register(@NotEmpty String code, @NotEmpty String encryptedData, @NotEmpty String iv) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                logger.info(code);
                JSONObject jscode2session = wxapi.code2Session(code);
                JSONObject userInfo = decrypt(encryptedData, jscode2session.getString("session_key"), iv);

                if (!jscode2session.getString("openid").equals(userInfo.getString("openId")) ||
                        !EnvironmentUtil.get("wx.appid").equals(userInfo.getJSONObject("watermark").getString("appid"))
                ) {
                    throw new BaseRuntimeException(ServiceCode.PARAM_IS_INVALID, "数据校验失败");
                }
                JxcCust jxcCust = new JxcCust();
                jxcCust.setOpenid(userInfo.getString("openId"));
                jxcCust.setAvatarUrl(userInfo.getString("avatarUrl"));
                jxcCust.setNickName(userInfo.getString("nickName"));
                jxcCustService.insert(jxcCust);

                //自动登录
                WxLoginUtil.login(userInfo.getString("openId"));
                JSONObject data = new JSONObject();
                data.put("user", WxLoginUtil.getJxcCust());
                data.put("token", LoginUtil.getToken());
                data.put("timeout", LoginUtil.getTimeout().getSeconds());
                data.put("unit", "SECONDS");
                resultJson.set(data);
            }
        }.result();
    }

    private JSONObject decrypt(String encryptedData, String sessionKey, String iv) {
        Base64.Decoder decoder = Base64.getDecoder();

        String str = CryptoUtils.aesDecrypt(
                decoder.decode(encryptedData.getBytes()),
                decoder.decode(sessionKey.getBytes()),
                decoder.decode(iv.getBytes()));
        return JSONObject.parseObject(str);

    }
}
