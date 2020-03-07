package group.zealot.king.demo.api.controller.wx;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.security.CryptoUtils;
import group.zealot.king.base.util.EnvironmentUtil;
import group.zealot.king.core.zt.entity.wx.WxUser;
import group.zealot.king.demo.api.config.LoginUtil;
import group.zealot.king.demo.api.config.ResultTemple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.Base64;

import static group.zealot.king.core.zt.dbif.Services.wxUserService;

/**
 * @author zealot
 * @date 2020/2/29 17:28
 */
@RestController
@RequestMapping("/wx")
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
                if (!LoginUtil.isLogin()) {
                    logger.info(code);
                    JSONObject jscode2session = wxapi.code2Session(code);
                    LoginUtil.wxlogin(jscode2session.getString("openid"));
                }
                data.put("user", LoginUtil.getWXUser());
                data.put("token", LoginUtil.getToken());
                data.put("timeout", LoginUtil.timeout.getSeconds());
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
                WxUser wxUser = new WxUser();
                wxUser.setOpenid(userInfo.getString("openId"));
                wxUser.setAvatarUrl(userInfo.getString("avatarUrl"));
                wxUser.setNickName(userInfo.getString("nickName"));
                wxUserService.insert(wxUser);

                //自动登录
                LoginUtil.wxlogin(userInfo.getString("openId"));
                JSONObject data = new JSONObject();
                data.put("user", LoginUtil.getWXUser());
                data.put("token", LoginUtil.getToken());
                data.put("timeout", LoginUtil.timeout.getSeconds());
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
