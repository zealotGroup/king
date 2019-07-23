package group.zealot.king.core.zt.mif;

import group.zealot.king.core.zt.mif.service.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Services {
    public static SysIdService sysIdService;
    public static SysUserService sysUserService;
    public static SysRouteService sysRouteService;
    public static SysAuthService sysAuthService;
    public static SysDataService sysDataService;
    public static SysRoleDataService sysRoleDataService;
    public static SysRoleRouteService sysRoleRouteService;

    @Autowired(required = false)
    public void setSysIdService(SysIdService sysIdService) {
        Services.sysIdService = sysIdService;
    }

    @Autowired(required = false)
    public void setSysUserService(SysUserService sysUserService) {
        Services.sysUserService = sysUserService;
    }

    @Autowired(required = false)
    public void setSysRouteService(SysRouteService sysRouteService) {
        Services.sysRouteService = sysRouteService;
    }

    @Autowired(required = false)
    public void setSysAuthService(SysAuthService sysAuthService) {
        Services.sysAuthService = sysAuthService;
    }

    @Autowired(required = false)
    public void setSysDataService(SysDataService sysDataService) {
        Services.sysDataService = sysDataService;
    }

    @Autowired(required = false)
    public void setSysRoleDataService(SysRoleDataService sysRoleDataService) {
        Services.sysRoleDataService = sysRoleDataService;
    }

    @Autowired(required = false)
    public void setSysRoleRouteService(SysRoleRouteService sysRoleRouteService) {
        Services.sysRoleRouteService = sysRoleRouteService;
    }
}
