package group.zealot.king.core.db.jpa;

import group.zealot.king.core.db.jpa.admin.AdminLableRepository;
import group.zealot.king.core.db.jpa.admin.AdminPictureRepository;
import group.zealot.king.core.db.jpa.admin.AdminUnitRepository;
import group.zealot.king.core.db.jpa.jxc.*;
import group.zealot.king.core.db.jpa.jxc.rel.JxcGoodsCustPriceRepository;
import group.zealot.king.core.db.jpa.jxc.rel.JxcGoodsCustShopcarRepository;
import group.zealot.king.core.db.jpa.jxc.rel.JxcGoodsLableRepository;
import group.zealot.king.core.db.jpa.jxc.rel.JxcGoodsPictureRepository;
import group.zealot.king.core.db.jpa.system.*;
import group.zealot.king.core.zt.aop.ZTComponent;

/**
 * @author zealot
 * @date 2020/2/9 12:41
 */
@ZTComponent
public class Repositorys {
    public static SysAuthRepository sysAuthRepository;
    public static SysDataRepository sysDataRepository;
    public static SysIdRepository sysIdRepository;
    public static SysRoleDataRepository sysRoleDataRepository;
    public static SysRoleRouteRepository sysRoleRouteRepository;
    public static SysRouteRepository sysRouteRepository;
    public static SysUserRepository sysUserRepository;

    public static JxcCustRepository jxcCustRepository;
    public static JxcGoodsRepository jxcGoodsRepository;
    public static JxcSalesRepository jxcSalesRepository;
    public static JxcStockRepository jxcStockRepository;
    public static JxcPurchaseRepository jxcPurchaseRepository;
    public static JxcSupplierRepository jxcSupplierRepository;

    public static JxcGoodsPictureRepository jxcGoodsPictureRepository;
    public static JxcGoodsCustShopcarRepository jxcGoodsCustShopcarRepository;
    public static JxcGoodsCustPriceRepository jxcGoodsCustPriceRepository;
    public static JxcGoodsLableRepository jxcGoodsLableRepository;

    public static AdminUnitRepository adminUnitRepository;
    public static AdminLableRepository adminLableRepository;
    public static AdminPictureRepository adminPictureRepository;
}
