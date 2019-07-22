package group.zealot.king.core.zt.mif;

import group.zealot.king.core.zt.mif.service.system.SysAuthService;
import group.zealot.king.core.zt.mif.service.system.SysIdService;
import group.zealot.king.core.zt.mif.service.system.SysRouteService;
import group.zealot.king.core.zt.mif.service.system.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Services {
    public static SysIdService sysIdService;
    public static SysUserService sysUserService;
    public static SysRouteService sysRouteService;
    public static SysAuthService sysAuthService;

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
}
