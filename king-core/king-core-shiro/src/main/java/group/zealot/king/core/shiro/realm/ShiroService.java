package group.zealot.king.core.shiro.realm;

import group.zealot.king.base.security.DigestUtils;
import group.zealot.king.base.util.EncodeUtil;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.shiro.exception.ShiroException;
import group.zealot.king.core.zt.mybatis.system.entity.SysUser;
import group.zealot.king.core.zt.mybatis.system.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiroService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    /**
     * 根据凭证token 获取有效shiro用户【包含参数校验。不包含password验证，密码验证交给shiro】
     */
    public ShiroUser validateShiroToken(ShiroToken shiroToken) throws AuthenticationException {
        if (shiroToken == null || StringUtil.isEmpty(shiroToken.getUsername())
                || StringUtil.isEmpty(String.copyValueOf(shiroToken.getPassword()))) {
            throw new ShiroException("shiroToken is illegal ( username null || password null || captcha null)");
        }
        return getShiroUser(shiroToken);
    }

    /**
     * 根据登录凭证获取 正确的shiroUser【比对交给shiro后续去比对】
     */
    protected ShiroUser getShiroUser(ShiroToken shiroToken) {
        SysUser sysUser = sysUserService.getByUsername(shiroToken.getUsername());
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setPassword(sysUser.getPassword());
        shiroUser.setUsername(sysUser.getUsername());
        shiroUser.setSalt(sysUser.getUsername());
        return shiroUser;
    }


    /**
     * 根据登录用户凭证，获取用户角色、权限
     */
    protected SimpleAuthorizationInfo getAuthorizationInfo(ShiroUser shiroUser) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return simpleAuthorizationInfo;
    }

    public boolean isLogin() throws ShiroException {
        Subject subject = SecurityUtils.getSubject();
        return subject.isAuthenticated();
    }

    public boolean isRemembered() throws ShiroException {
        Subject subject = SecurityUtils.getSubject();
        return subject.isRemembered();
    }

    public void shiroLogin(String username, String password) throws ShiroException {
        Subject subject = SecurityUtils.getSubject();
        // 调用安全认证框架的登录方法
        subject.login(new ShiroToken(username, password.toCharArray()));
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    protected CredentialsMatcher getCredentialsMatcher() {
        logger.info(
                "shiro 采用 HashedCredentialsMatcher:hashAlgorithmName " + getHashAlgorithmName() + " hashIterations" +
                        getHashIterations());
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(getHashAlgorithmName());
        matcher.setHashIterations(getHashIterations());
        return matcher;
    }

    /**
     * 设定安全的密码
     */
    public String entryptPassword(byte[] password, byte[] salt) {
        byte[] hashPassword = DigestUtils.sha1(password, salt, getHashIterations());
        return EncodeUtil.encodeHex(hashPassword);
    }

    /**
     * entryptPassword加密的类型sha-1
     *
     * @return
     */
    protected String getHashAlgorithmName() {
        return "SHA-1";
    }

    /**
     * entryptPassword加密的hash迭代次数
     *
     * @return
     */
    protected int getHashIterations() {
        return 1024;
    }
}