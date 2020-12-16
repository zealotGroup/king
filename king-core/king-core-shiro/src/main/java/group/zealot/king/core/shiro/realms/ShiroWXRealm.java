package group.zealot.king.core.shiro.realms;

import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.shiro.exception.ShiroException;
import group.zealot.king.core.zt.entity.jxc.JxcCust;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static group.zealot.king.core.zt.dbif.Services.*;


@Component
public class ShiroWXRealm extends BaseShiroRealm<JxcCust> {
    @Autowired
    public ShiroWXRealm(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    public boolean doSupports(ShiroToken token) {
        return StringUtil.isNotEmpty(token.getOpenid());
    }

    @Override
    protected JxcCust getPrincipal(ShiroToken token) throws AuthenticationException {
        JxcCust jxcCust = new JxcCust();
        jxcCust.setOpenid(token.getOpenid());
        jxcCust = jxcCustService.get(jxcCust);
        if (jxcCust == null) {
            throw new ShiroException();
        }
        jxcCust.setSessionKey(token.getSessionKey());
        return jxcCust;
    }

    @Override
    protected SimpleAuthorizationInfo getSimpleAuthorizationInfo(JxcCust principal) {
        return new SimpleAuthorizationInfo();
    }


}