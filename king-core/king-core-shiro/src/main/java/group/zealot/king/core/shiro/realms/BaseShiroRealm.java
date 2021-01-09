package group.zealot.king.core.shiro.realms;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;


public abstract class BaseShiroRealm<T> extends AuthorizingRealm {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public BaseShiroRealm(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    public String getAuthorizationCacheName() {
        return "shiro:realm:" + getClass().getSimpleName();
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof ShiroToken) {
            return doSupports((ShiroToken) token);
        } else {
            return false;
        }
    }


    /**
     * 认证回调函数, 登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        T principal = getPrincipal((ShiroToken) token);
        return new SimpleAuthenticationInfo(principal, token.getCredentials(), getAuthorizationCacheName());
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = getSimpleAuthorizationInfo((T) principal.getPrimaryPrincipal());
        logger.info("roles : " + JSONObject.toJSONString(simpleAuthorizationInfo.getRoles()));
        logger.info("permissions : " + JSONObject.toJSONString(simpleAuthorizationInfo.getStringPermissions()));
        return simpleAuthorizationInfo;
    }

    abstract protected boolean doSupports(ShiroToken token);

    abstract protected T getPrincipal(ShiroToken token) throws AuthenticationException;

    abstract protected SimpleAuthorizationInfo getSimpleAuthorizationInfo(T principal);

    protected byte[] getBytes(char[] chars) {
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = Charset.forName("UTF-8").encode(cb);
        return bb.array();
    }
}