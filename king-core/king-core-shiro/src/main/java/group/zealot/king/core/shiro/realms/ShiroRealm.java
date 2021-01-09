package group.zealot.king.core.shiro.realms;

import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.shiro.exception.ShiroException;
import group.zealot.king.core.zt.Passwd;
import group.zealot.king.core.zt.entity.system.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static group.zealot.king.core.zt.dbif.Services.sysAuthService;
import static group.zealot.king.core.zt.dbif.Services.sysUserService;


@Component
public class ShiroRealm extends BaseShiroRealm<SysUser> {
    @Autowired
    public ShiroRealm(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    public boolean doSupports(ShiroToken token) {
        return StringUtil.isNotEmpty(token.getUsername()) && token.getPassword() != null && token.getPassword().length > 0;
    }

    @Override
    protected SysUser getPrincipal(ShiroToken token) throws AuthenticationException {
        SysUser sysUser = sysUserService.getByUsername(token.getUsername());
        String newPassword = Passwd.getNewPassword(token.getPassword(), getBytes(token.getUsername().toCharArray()));
        if (sysUser == null || !sysUser.getPassword().equals(newPassword)) {
            throw new ShiroException();
        }
        return sysUser;
    }

    @Override
    protected SimpleAuthorizationInfo getSimpleAuthorizationInfo(SysUser principal) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //数据权限
        SysRoleData sysRoleData = sysAuthService.getSysRoleData(principal.getId());
        List<SysData> sysDataList = sysAuthService.getSysData(sysRoleData.getId());
        sysDataList.forEach(sysData -> simpleAuthorizationInfo.addRole(sysData.getId().toString()));
        //路由权限
        SysRoleRoute sysRoleRoute = sysAuthService.getSysRoleRoute(principal.getId());
        List<SysRoute> sysRouteList = sysAuthService.getSysRoute(sysRoleRoute.getId());
        sysRouteList.forEach(sysRoute -> simpleAuthorizationInfo.addStringPermission(sysRoute.getId().toString()));

        return simpleAuthorizationInfo;
    }
}