package group.zealot.king.core.shiro.sessionListener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ShiroSessionListener implements SessionListener {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onStart(Session session) {
        logger.info("会话创建sessionId：" + session.getId());
    }

    @Override
    public void onStop(Session session) {
        logger.info("会话退出/过期sessionId：" + session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        logger.info("会话过期sessionId：" + session.getId());
    }
}
