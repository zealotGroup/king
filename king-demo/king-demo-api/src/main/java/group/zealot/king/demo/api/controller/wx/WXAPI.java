package group.zealot.king.demo.api.controller.wx;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.security.CryptoUtils;
import group.zealot.king.base.util.EnvironmentUtil;
import group.zealot.king.base.util.HttpUtil;
import group.zealot.king.core.zt.aop.ZTValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * @author zealot
 * @date 2020/2/29 17:38
 */
@Component
public class WXAPI {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public JSONObject code2Session(@ZTValid(NotBlank = true) String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        url = String.format(url, EnvironmentUtil.get("wx.appid"), EnvironmentUtil.get("wx.secret"), code);
        String str = HttpUtil.get(url);
        logger.info(str);
        JSONObject jscode2session = JSONObject.parseObject(str);
        if (jscode2session.getIntValue("errcode") != 0) {
            throw new BaseRuntimeException(jscode2session.getString("errmsg"));
        }
        return jscode2session;
    }

    public JSONObject decrypt(String encryptedData, String sessionKey, String iv) {
        Base64.Decoder decoder = Base64.getDecoder();

        String str = CryptoUtils.aesDecrypt(
                decoder.decode(encryptedData.getBytes()),
                decoder.decode(sessionKey.getBytes()),
                decoder.decode(iv.getBytes()));
        return JSONObject.parseObject(str);

    }

}
