package group.zealot.king.demo.api.controller.wx;


import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.shiro.LoginUtil;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.jxc.JxcCust;
import group.zealot.king.demo.api.config.ResultTemple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.jxcCustService;

/**
 * @author zealot
 * @date 2020/3/8 14:15
 */
@RestController
@RequestMapping("/wx/my")
public class WXMyController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WXAPI wxapi;

    @RequestMapping("updatePhoneNumber")
    public JSONObject updatePhoneNumber(@ZTValid(NotNull = true) String encryptedData, @ZTValid(NotNull = true) String iv) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                JSONObject info = wxapi.decrypt(encryptedData, LoginUtil.getWxSessionKey(), iv);
                JxcCust wxUser = LoginUtil.getWxUser();
                wxUser.setPhoneNumber(info.getString("phoneNumber"));
                jxcCustService.update(wxUser);
                JSONObject data = new JSONObject();
                data.put("phoneNumber", wxUser.getPhoneNumber());
                resultJson.set(data);
            }
        }.result();
    }
}
