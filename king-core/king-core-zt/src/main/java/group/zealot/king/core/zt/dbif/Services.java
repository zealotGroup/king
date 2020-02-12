package group.zealot.king.core.zt.dbif;

import group.zealot.king.core.zt.dbif.service.jxc.*;
import group.zealot.king.core.zt.dbif.service.system.*;
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
    public static JxcChannelService jxcChannelService;
    public static JxcGoodsLableService jxcGoodsLableService;
    public static JxcGoodsService jxcGoodsService;
    public static JxcLableService jxcLableService;
    public static JxcPurchaseSalesService jxcPurchaseSalesService;
    public static JxcStockService jxcStockService;

    @Autowired(required = false)
    public void setJxcChannelService(JxcChannelService jxcChannelService) {
        Services.jxcChannelService = jxcChannelService;
    }

    @Autowired(required = false)
    public void setJxcGoodsLableService(JxcGoodsLableService jxcGoodsLableService) {
        Services.jxcGoodsLableService = jxcGoodsLableService;
    }

    @Autowired(required = false)
    public void setJxcGoodsService(JxcGoodsService jxcGoodsService) {
        Services.jxcGoodsService = jxcGoodsService;
    }

    @Autowired(required = false)
    public void setJxcLableService(JxcLableService jxcLableService) {
        Services.jxcLableService = jxcLableService;
    }

    @Autowired(required = false)
    public void setJxcPurchaseSalesService(JxcPurchaseSalesService jxcPurchaseSalesService) {
        Services.jxcPurchaseSalesService = jxcPurchaseSalesService;
    }

    @Autowired(required = false)
    public void setJxcStockService(JxcStockService jxcStockService) {
        Services.jxcStockService = jxcStockService;
    }

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
