package group.zealot.king.core.zt.dbif;

import group.zealot.king.core.zt.aop.ZTComponent;
import group.zealot.king.core.zt.dbif.service.admin.AdminLableService;
import group.zealot.king.core.zt.dbif.service.admin.AdminPictureService;
import group.zealot.king.core.zt.dbif.service.admin.AdminUnitService;
import group.zealot.king.core.zt.dbif.service.jxc.*;
import group.zealot.king.core.zt.dbif.service.jxc.rel.JxcGoodsCustPriceService;
import group.zealot.king.core.zt.dbif.service.jxc.rel.JxcGoodsCustShopcarService;
import group.zealot.king.core.zt.dbif.service.jxc.rel.JxcGoodsLableService;
import group.zealot.king.core.zt.dbif.service.jxc.rel.JxcGoodsPictureService;
import group.zealot.king.core.zt.dbif.service.system.*;

@ZTComponent
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

}
