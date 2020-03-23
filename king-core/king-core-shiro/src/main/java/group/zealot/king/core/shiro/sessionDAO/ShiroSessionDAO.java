package group.zealot.king.core.shiro.sessionDAO;

import group.zealot.king.core.shiro.exception.ShiroException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.session.mgt.eis.RandomSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ShiroSessionDAO extends CachingSessionDAO {

    private SessionIdGenerator sessionIdGenerator = new RandomSessionIdGenerator();

    @Autowired
    private CacheManager cacheManager;

    public ShiroSessionDAO() {
        super();
        setCacheManager(cacheManager);
        setActiveSessionsCacheName("shiro:cache:ShiroSessionDAO");
        setSessionIdGenerator(sessionIdGenerator);
        createActiveSessionsCache();//优先创建缓存，防止线程安全问题出现
    }

    @Override
    protected Serializable doCreate(Session session) {
        SimpleSession simpleSession = (SimpleSession) session;
        Serializable id = generateSessionId(session);
        simpleSession.setId(id);
        return id;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        throw new ShiroException("There is no session with id [" + sessionId + "]");
    }

    @Override
    protected void doUpdate(Session session) {
    }

    @Override
    protected void doDelete(Session session) {
    }
}
