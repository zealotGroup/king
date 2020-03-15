package group.zealot.king.core.db.serviceimpl;

import group.zealot.king.core.db.serviceimpl.admin.*;
import group.zealot.king.core.db.serviceimpl.jxc.*;
import group.zealot.king.core.db.serviceimpl.jxc.rel.JxcGoodsCustPriceServiceImpl;
import group.zealot.king.core.db.serviceimpl.jxc.rel.JxcGoodsLableServiceImpl;
import group.zealot.king.core.db.serviceimpl.jxc.rel.JxcGoodsPictureServiceImpl;
import group.zealot.king.core.db.serviceimpl.jxc.rel.JxcGoodsCustShopcarServiceImpl;
import group.zealot.king.core.db.serviceimpl.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zealot
 * @date 2020/2/14 11:06
 */
@Component
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

    public static JxcGoodsPictureServiceImpl jxcGoodsPictureServiceImpl;
    public static JxcGoodsCustShopcarServiceImpl jxcGoodsCustShopCarServiceImpl;
    public static JxcGoodsCustPriceServiceImpl jxcGoodsCustPriceServiceImpl;
    public static JxcGoodsLableServiceImpl jxcGoodsLableService;

    public static AdminLableServiceImpl adminLableServiceImpl;
    public static AdminPictureServiceImpl adminPictureServiceImpl;
    public static AdminUnitServiceImpl adminUnitServiceImpl;

    @Autowired(required = false)
    public void setJxcGoodsLableService(JxcGoodsLableServiceImpl jxcGoodsLableService) {
        ServiceImpls.jxcGoodsLableService = jxcGoodsLableService;
    }

    @Autowired(required = false)
    public void setJxcGoodsCustShopCarServiceImpl(JxcGoodsCustShopcarServiceImpl jxcGoodsCustShopCarServiceImpl) {
        ServiceImpls.jxcGoodsCustShopCarServiceImpl = jxcGoodsCustShopCarServiceImpl;
    }

    @Autowired(required = false)
    public void setJxcGoodsCustPriceServiceImpl(JxcGoodsCustPriceServiceImpl jxcGoodsCustPriceServiceImpl) {
        ServiceImpls.jxcGoodsCustPriceServiceImpl = jxcGoodsCustPriceServiceImpl;
    }

    @Autowired(required = false)
    public void setJxcGoodsPictureServiceImpl(JxcGoodsPictureServiceImpl jxcGoodsPictureServiceImpl) {
        ServiceImpls.jxcGoodsPictureServiceImpl = jxcGoodsPictureServiceImpl;
    }

    @Autowired(required = false)
    public void setJxcSupplierServiceImpl(JxcSupplierServiceImpl jxcSupplierServiceImpl) {
        ServiceImpls.jxcSupplierServiceImpl = jxcSupplierServiceImpl;
    }

    @Autowired(required = false)
    public void setSysAuthServiceImpl(SysAuthServiceImpl sysAuthServiceImpl) {
        ServiceImpls.sysAuthServiceImpl = sysAuthServiceImpl;
    }

    @Autowired(required = false)
    public void setSysDataServiceImpl(SysDataServiceImpl sysDataServiceImpl) {
        ServiceImpls.sysDataServiceImpl = sysDataServiceImpl;
    }

    @Autowired(required = false)
    public void setSysIdServiceImpl(SysIdServiceImpl sysIdServiceImpl) {
        ServiceImpls.sysIdServiceImpl = sysIdServiceImpl;
    }

    @Autowired(required = false)
    public void setSysRoleDataServiceImpl(SysRoleDataServiceImpl sysRoleDataServiceImpl) {
        ServiceImpls.sysRoleDataServiceImpl = sysRoleDataServiceImpl;
    }

    @Autowired(required = false)
    public void setSysRoleRouteServiceImpl(SysRoleRouteServiceImpl sysRoleRouteServiceImpl) {
        ServiceImpls.sysRoleRouteServiceImpl = sysRoleRouteServiceImpl;
    }

    @Autowired(required = false)
    public void setSysRouteServiceImpl(SysRouteServiceImpl sysRouteServiceImpl) {
        ServiceImpls.sysRouteServiceImpl = sysRouteServiceImpl;
    }

    @Autowired(required = false)
    public void setSysUserServiceImpl(SysUserServiceImpl sysUserServiceImpl) {
        ServiceImpls.sysUserServiceImpl = sysUserServiceImpl;
    }

    @Autowired(required = false)
    public void setJxcCustServiceImpl(JxcCustServiceImpl jxcCustServiceImpl) {
        ServiceImpls.jxcCustServiceImpl = jxcCustServiceImpl;
    }

    @Autowired(required = false)
    public void setJxcGoodsLableServiceImpl(JxcGoodsLableServiceImpl jxcGoodsLableServiceImpl) {
        ServiceImpls.jxcGoodsLableServiceImpl = jxcGoodsLableServiceImpl;
    }

    @Autowired(required = false)
    public void setJxcGoodsServiceImpl(JxcGoodsServiceImpl jxcGoodsServiceImpl) {
        ServiceImpls.jxcGoodsServiceImpl = jxcGoodsServiceImpl;
    }

    @Autowired(required = false)
    public void setJxcPurchaseSalesServiceImpl(JxcPurchaseServiceImpl jxcPurchaseSalesServiceImpl) {
        ServiceImpls.jxcPurchaseSalesServiceImpl = jxcPurchaseSalesServiceImpl;
    }

    @Autowired(required = false)
    public void setJxcStockServiceImpl(JxcStockServiceImpl jxcStockServiceImpl) {
        ServiceImpls.jxcStockServiceImpl = jxcStockServiceImpl;
    }

    @Autowired(required = false)
    public void setAdminLableServiceImpl(AdminLableServiceImpl adminLableServiceImpl) {
        ServiceImpls.adminLableServiceImpl = adminLableServiceImpl;
    }

    @Autowired(required = false)
    public void setAdminPictureServiceImpl(AdminPictureServiceImpl adminPictureServiceImpl) {
        ServiceImpls.adminPictureServiceImpl = adminPictureServiceImpl;
    }

    @Autowired(required = false)
    public void setAdminUnitServiceImpl(AdminUnitServiceImpl adminUnitServiceImpl) {
        ServiceImpls.adminUnitServiceImpl = adminUnitServiceImpl;
    }
}
