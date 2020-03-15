package group.zealot.king.demo.api.config;

import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.entity.jxc.JxcCust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static group.zealot.king.core.zt.dbif.Services.jxcCustService;

public class WxLoginUtil extends BaseLoginUtil {
    protected static Logger logger = LoggerFactory.getLogger(WxLoginUtil.class);

    public static String login(String openid) {
        JxcCust jxcCust = new JxcCust();
        jxcCust.setOpenid(openid);
        jxcCust = jxcCustService.get(jxcCust);
        if (jxcCust == null) {
            throw new BaseRuntimeException(ServiceCode.NO_USER);
        }
        return createToken(jxcCust);
    }

    public static JxcCust getJxcCust() {
        return getSession();
    }
}
