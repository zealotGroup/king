package group.zealot.king.core.shiro.realm;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.shiro.exception.ShiroException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ShiroRealm extends AuthorizingRealm {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected ShiroService shiroService;

    @Autowired
    public ShiroRealm(CacheManager cacheManager){
        super(cacheManager);
    }

    /**
     * 认证回调函数, 登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        ShiroToken token = (ShiroToken) authcToken;
        ShiroUser shiroUser = shiroService.validateShiroToken(token);
        return new SimpleAuthenticationInfo(shiroUser,
                shiroUser.getPassword(), ByteSource.Util.bytes(shiroUser.getSalt()), getName());
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = shiroService.createAuthorizationInfo(shiroUser);
        logger.info("roles : " + JSONObject.toJSONString(simpleAuthorizationInfo.getRoles()));
        logger.info("permissions : " + JSONObject.toJSONString(simpleAuthorizationInfo.getStringPermissions()));
        return simpleAuthorizationInfo;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        CredentialsMatcher matcher = shiroService.getCredentialsMatcher();
        setCredentialsMatcher(matcher);
    }

    public SimpleAuthorizationInfo getSimpleAuthorizationInfo() {
        Object principals = getAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
        if (principals instanceof SimpleAuthorizationInfo) {
            return (SimpleAuthorizationInfo) principals;
        } else {
            throw new ShiroException("shiro 获取 subject 再获取 principals，发现类型异常或为null");
        }
    }
}