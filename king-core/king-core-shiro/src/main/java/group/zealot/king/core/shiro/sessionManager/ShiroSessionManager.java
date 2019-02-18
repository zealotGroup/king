package group.zealot.king.core.shiro.sessionManager;

import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.stereotype.Component;

@Component
public class ShiroSessionManager extends DefaultWebSessionManager {
    ShiroSessionManager() {
        Cookie cookie = new SimpleCookie("ShiroSessionId");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 60 * 1000);//30min 过期 -1关闭浏览器过期
        setSessionIdCookie(cookie);
        setSessionIdCookieEnabled(true);
        setSessionIdUrlRewritingEnabled(true);
        setGlobalSessionTimeout(30 * 60 * 1000);//30min 过期
    }
}
