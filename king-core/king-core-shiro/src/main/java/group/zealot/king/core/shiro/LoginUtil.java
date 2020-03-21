package group.zealot.king.core.shiro;

import group.zealot.king.core.shiro.realms.ShiroToken;
import group.zealot.king.core.zt.entity.jxc.JxcCust;
import group.zealot.king.core.zt.entity.system.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUtil {
    protected static Logger logger = LoggerFactory.getLogger(LoginUtil.class);

    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static boolean isLogin() {
        return getSubject().isAuthenticated();
    }

    public static void login(ShiroToken shiroToken) {
        getSubject().login(shiroToken);
    }

    public static void logout() {
        getSubject().logout();
    }

    public static <T> T getUser() {
        return (T) getSubject().getPrincipal();
    }

    public static JxcCust getJxcCust() {
        return getUser();
    }

    public static SysUser getSysUser() {
        return getUser();
    }

    public static Session getSession() {
        return getSubject().getSession();
    }
}
