package group.zealot.king.core.zt.dbif;

import group.zealot.king.core.zt.dbif.service.admin.AdminLableService;
import group.zealot.king.core.zt.dbif.service.admin.AdminPictureService;
import group.zealot.king.core.zt.dbif.service.admin.AdminUnitService;
import group.zealot.king.core.zt.dbif.service.jxc.*;
import group.zealot.king.core.zt.dbif.service.jxc.rel.*;
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

    public static AdminPictureService adminPictureService;
    public static AdminUnitService adminUnitService;
    public static AdminLableService adminLableService;

    public static JxcCustService jxcCustService;
    public static JxcGoodsService jxcGoodsService;
    public static JxcStockService jxcStockService;
    public static JxcSupplierService jxcSupplierService;
    public static JxcSalesService jxcSalesService;
    public static JxcPurchaseService jxcPurchaseService;

    public static JxcGoodsCustPriceService jxcGoodsCustPriceService;
    public static JxcGoodsCustShopcarService jxcGoodsCustShopcarService;
    public static JxcGoodsLableService jxcGoodsLableService;
    public static JxcGoodsPictureService jxcGoodsPictureService;


    @Autowired
    public void setJxcGoodsCustPriceService(JxcGoodsCustPriceService jxcGoodsCustPriceService) {
        Services.jxcGoodsCustPriceService = jxcGoodsCustPriceService;
    }

    @Autowired
    public void setJxcGoodsCustShopcarService(JxcGoodsCustShopcarService jxcGoodsCustShopcarService) {
        Services.jxcGoodsCustShopcarService = jxcGoodsCustShopcarService;
    }

    @Autowired
    public void setJxcGoodsLableService(JxcGoodsLableService jxcGoodsLableService) {
        Services.jxcGoodsLableService = jxcGoodsLableService;
    }

    @Autowired
    public void setJxcGoodsPictureService(JxcGoodsPictureService jxcGoodsPictureService) {
        Services.jxcGoodsPictureService = jxcGoodsPictureService;
    }

    @Autowired
    public void setSysIdService(SysIdService sysIdService) {
        Services.sysIdService = sysIdService;
    }

    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        Services.sysUserService = sysUserService;
    }

    @Autowired
    public void setSysRouteService(SysRouteService sysRouteService) {
        Services.sysRouteService = sysRouteService;
    }

    @Autowired
    public void setSysAuthService(SysAuthService sysAuthService) {
        Services.sysAuthService = sysAuthService;
    }

    @Autowired
    public void setSysDataService(SysDataService sysDataService) {
        Services.sysDataService = sysDataService;
    }

    @Autowired
    public void setSysRoleDataService(SysRoleDataService sysRoleDataService) {
        Services.sysRoleDataService = sysRoleDataService;
    }

    @Autowired
    public void setSysRoleRouteService(SysRoleRouteService sysRoleRouteService) {
        Services.sysRoleRouteService = sysRoleRouteService;
    }

    @Autowired
    public void setAdminPictureService(AdminPictureService adminPictureService) {
        Services.adminPictureService = adminPictureService;
    }

    @Autowired
    public void setAdminUnitService(AdminUnitService adminUnitService) {
        Services.adminUnitService = adminUnitService;
    }

    @Autowired
    public void setAdminLableService(AdminLableService adminLableService) {
        Services.adminLableService = adminLableService;
    }

    @Autowired
    public void setJxcCustService(JxcCustService jxcCustService) {
        Services.jxcCustService = jxcCustService;
    }

    @Autowired
    public void setJxcGoodsService(JxcGoodsService jxcGoodsService) {
        Services.jxcGoodsService = jxcGoodsService;
    }

    @Autowired
    public void setJxcStockService(JxcStockService jxcStockService) {
        Services.jxcStockService = jxcStockService;
    }

    @Autowired
    public void setJxcSupplierService(JxcSupplierService jxcSupplierService) {
        Services.jxcSupplierService = jxcSupplierService;
    }

    @Autowired
    public void setJxcSalesService(JxcSalesService jxcSalesService) {
        Services.jxcSalesService = jxcSalesService;
    }

    @Autowired
    public void setJxcPurchaseService(JxcPurchaseService jxcPurchaseService) {
        Services.jxcPurchaseService = jxcPurchaseService;
    }
}
