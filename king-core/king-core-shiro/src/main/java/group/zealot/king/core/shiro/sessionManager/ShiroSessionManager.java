package group.zealot.king.core.shiro.sessionManager;

import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

import static group.zealot.king.core.shiro.ShiroConfig.sessionTimeout;

@Component
public class ShiroSessionManager extends DefaultWebSessionManager {
    private final static Logger logger = LoggerFactory.getLogger(ShiroSessionManager.class);
    public final static String AUTH_TOKEN = "auth-token";
    public static final String HEADER_SESSION_ID_SOURCE = "header";

    public ShiroSessionManager() {
    }

    @Autowired
    public void setDefault(Cookie cookie, SessionValidationScheduler sessionValidationScheduler,
                           SessionFactory sessionFactory, SessionDAO sessionDAO) {
        setSessionIdCookie(cookie);
        setGlobalSessionTimeout(sessionTimeout);//30min 过期
        setSessionValidationScheduler(sessionValidationScheduler);
        setSessionFactory(sessionFactory);
        setSessionDAO(sessionDAO);
        setSessionIdUrlRewritingEnabled(false);//可隐藏sessionId后缀
    }

    @Override
    protected void onStart(Session session, SessionContext context) {
        super.onStart(session, context);
        onStartHeader(session, context);
    }

    protected void onStartHeader(Session session, SessionContext context) {
        if (!WebUtils.isHttp(context)) {
            logger.debug("SessionContext不是HTTP请求. sessionId无法set到Header中");
        } else {
            HttpServletResponse response = WebUtils.getHttpResponse(context);
            Serializable sessionId = session.getId();
            String id = sessionId.toString();
            response.setHeader(AUTH_TOKEN, id);
        }
    }

    @Override
    protected void onStop(Session session, SessionKey key) {
        super.onStop(session, key);
        onStopHeader(key);
    }

    protected void onStopHeader(SessionKey key) {
        if (!WebUtils.isHttp(key)) {
            logger.debug("SessionKey不是HTTP请求. sessionId无法从Header中remove");
        } else {
            removeSessionHeader(WebUtils.getHttpResponse(key));
        }
    }

    private void removeSessionHeader(HttpServletResponse response) {
        logger.debug("Removing session ID Header.");
        response.setHeader(AUTH_TOKEN, "deleteMe");//覆盖
    }

    @Override
    protected void onInvalidation(Session session, InvalidSessionException ise, SessionKey key) {
        super.onInvalidation(session, ise, key);
        onInvalidationHeader(key);
    }

    protected void onInvalidationHeader(SessionKey key) {
        if (!WebUtils.isHttp(key)) {
//            logger.debug("SessionKey不是HTTP请求. sessionId无法set到Header中");
        } else {
            logger.debug("Removing session ID Header.");
            removeSessionHeader(WebUtils.getHttpResponse(key));
        }
    }

    @Override
    protected void onExpiration(Session s, ExpiredSessionException ese, SessionKey key) {
        super.onExpiration(s, ese, key);
        this.onInvalidationHeader(key);
    }

    /**
     * 一般先从 cookie，然后url上，接着params。最后定义的header
     */
    @Override
    public Serializable getSessionId(SessionKey key) {
        Serializable id = super.getSessionId(key);
        if (id == null && WebUtils.isWeb(key)) {
            ServletRequest request = WebUtils.getRequest(key);
            id = getSessionIdHeader(request);
        }
        return id;
    }

    protected Serializable getSessionIdHeader(ServletRequest request) {
        Serializable id = getRequestSessionIdHeader(request);
        if (id != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                    HEADER_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, Boolean.FALSE);
        }
        return id;
    }

    protected Serializable getRequestSessionIdHeader(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            logger.debug("request 不是HttpServletRequest 类型，无法获取Header");
            return null;
        } else {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            // 在request 中 读取 x-auth-token 信息  作为 sessionId
            return httpRequest.getHeader(AUTH_TOKEN);
        }
    }
}
