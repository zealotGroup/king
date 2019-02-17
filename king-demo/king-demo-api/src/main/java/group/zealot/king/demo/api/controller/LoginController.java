package group.zealot.king.demo.api.controller;

import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.demo.api.ResultJson;
import group.zealot.king.demo.api.ResultJsonFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/login")
    public String login(String username, String password) {
        ResultJson resultJson = ResultJsonFactory.create();
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            return resultJson.set(ServiceCode.REQUEST_ERROR).toJSONString();
        } else {
            return resultJson.set(ServiceCode.SUCCESS).put("sessionId", "123").toJSONString();
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        String sessionId = request.getRequestedSessionId();
        ResultJson resultJson = ResultJsonFactory.create();
        if ("123".equals(sessionId)) {
            return resultJson.set(ServiceCode.REQUEST_ERROR).toJSONString();
        } else {
            return resultJson.set(ServiceCode.SUCCESS).toJSONString();
        }
    }
}
