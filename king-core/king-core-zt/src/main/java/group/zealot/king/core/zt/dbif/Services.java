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
    public static JxcCustService jxcCustService;
    public static JxcGoodsLableService jxcGoodsLableService;
    public static JxcGoodsService jxcGoodsService;
    public static JxcLableService jxcLableService;
    public static JxcPurchaseService jxcPurchaseService;
    public static JxcStockService jxcStockService;
    public static JxcPictureService jxcPictureService;

    @Autowired(required = false)
    public void setJxcCustService(JxcCustService jxcCustService) {
        Services.jxcCustService = jxcCustService;
    }

    @Autowired(required = false)
    public void setJxcPurchaseService(JxcPurchaseService jxcPurchaseService) {
        Services.jxcPurchaseService = jxcPurchaseService;
    }

    @Autowired(required = false)
    public void setJxcPictureService(JxcPictureService jxcPictureService) {
        Services.jxcPictureService = jxcPictureService;
    }

    @Autowired(required = false)
    public void setJxcChannelService(JxcCustService jxcCustService) {
        Services.jxcCustService = jxcCustService;
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
    public void setJxcPurchaseSalesService(JxcPurchaseService jxcPurchaseService) {
        Services.jxcPurchaseService = jxcPurchaseService;
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
