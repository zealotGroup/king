package group.zealot.king.core.db.serviceimpl;

import group.zealot.king.core.db.serviceimpl.admin.AdminLableServiceImpl;
import group.zealot.king.core.db.serviceimpl.admin.AdminPictureServiceImpl;
import group.zealot.king.core.db.serviceimpl.admin.AdminUnitServiceImpl;
import group.zealot.king.core.db.serviceimpl.jxc.*;
import group.zealot.king.core.db.serviceimpl.jxc.rel.JxcGoodsCustPriceServiceImpl;
import group.zealot.king.core.db.serviceimpl.jxc.rel.JxcGoodsCustShopcarServiceImpl;
import group.zealot.king.core.db.serviceimpl.jxc.rel.JxcGoodsLableServiceImpl;
import group.zealot.king.core.db.serviceimpl.jxc.rel.JxcGoodsPictureServiceImpl;
import group.zealot.king.core.db.serviceimpl.system.*;
import group.zealot.king.core.zt.aop.ZTComponent;

/**
 * @author zealot
 * @date 2020/2/14 11:06
 */
@ZTComponent
public class ServiceImpls {
    public static SysAuthServiceImpl sysAuthServiceImpl;
    public static SysDataServiceImpl sysDataServiceImpl;
    public static SysIdServiceImpl sysIdServiceImpl;
    public static SysRoleDataServiceImpl sysRoleDataServiceImpl;
    public static SysRoleRouteServiceImpl sysRoleRouteServiceImpl;
    public static SysRouteServiceImpl sysRouteServiceImpl;
    public static SysUserServiceImpl sysUserServiceImpl;

    public static JxcCustServiceImpl jxcCustServiceImpl;
    public static JxcGoodsLableServiceImpl jxcGoodsLableServiceImpl;
    public static JxcGoodsServiceImpl jxcGoodsServiceImpl;
    public static JxcPurchaseServiceImpl jxcPurchaseSalesServiceImpl;
    public static JxcStockServiceImpl jxcStockServiceImpl;
    public static JxcSupplierServiceImpl jxcSupplierServiceImpl;
    public static JxcSalesServiceImpl jxcSalesServiceImpl;

    public static JxcGoodsPictureServiceImpl jxcGoodsPictureServiceImpl;
    public static JxcGoodsCustShopcarServiceImpl jxcGoodsCustShopCarServiceImpl;
    public static JxcGoodsCustPriceServiceImpl jxcGoodsCustPriceServiceImpl;

    public static AdminLableServiceImpl adminLableServiceImpl;
    public static AdminPictureServiceImpl adminPictureServiceImpl;
    public static AdminUnitServiceImpl adminUnitServiceImpl;
}
