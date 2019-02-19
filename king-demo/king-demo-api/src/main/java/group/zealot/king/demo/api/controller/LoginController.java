package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.demo.api.config.ResultJson;
import group.zealot.king.demo.api.config.ResultJsonFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static group.zealot.king.demo.api.config.RequestFilter.TOKEN_NAME;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/login")
    public JSONObject login(String username, String password) {
        ResultJson resultJson = ResultJsonFactory.create();
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            return resultJson.set(ServiceCode.REQUEST_ERROR).result();
        } else {
            return resultJson.set(ServiceCode.SUCCESS).put(TOKEN_NAME, "123").result();
        }
    }

    @RequestMapping("/logout")
    public ResultJson logout(HttpServletRequest request) {
        String sessionId = request.getRequestedSessionId();
        ResultJson resultJson = ResultJsonFactory.create();
        if ("123".equals(sessionId)) {
            return resultJson.set(ServiceCode.REQUEST_ERROR);
        } else {
            return resultJson.set(ServiceCode.SUCCESS);
        }
    }
}
