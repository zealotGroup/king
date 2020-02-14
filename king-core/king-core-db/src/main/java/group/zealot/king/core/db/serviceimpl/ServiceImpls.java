package group.zealot.king.core.db.serviceimpl;

import group.zealot.king.core.db.serviceimpl.jxc.*;
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

    public static JxcChannelServiceImpl jxcChannelServiceImpl;
    public static JxcGoodsLableServiceImpl jxcGoodsLableServiceImpl;
    public static JxcGoodsServiceImpl jxcGoodsServiceImpl;
    public static JxcLableServiceImpl jxcLableServiceImpl;
    public static JxcPurchaseSalesServiceImpl jxcPurchaseSalesServiceImpl;
    public static JxcStockServiceImpl jxcStockServiceImpl;

    @Autowired
    public void setSysAuthServiceImpl(SysAuthServiceImpl sysAuthServiceImpl) {
        ServiceImpls.sysAuthServiceImpl = sysAuthServiceImpl;
    }

    @Autowired
    public void setSysDataServiceImpl(SysDataServiceImpl sysDataServiceImpl) {
        ServiceImpls.sysDataServiceImpl = sysDataServiceImpl;
    }

    @Autowired
    public void setSysIdServiceImpl(SysIdServiceImpl sysIdServiceImpl) {
        ServiceImpls.sysIdServiceImpl = sysIdServiceImpl;
    }

    @Autowired
    public void setSysRoleDataServiceImpl(SysRoleDataServiceImpl sysRoleDataServiceImpl) {
        ServiceImpls.sysRoleDataServiceImpl = sysRoleDataServiceImpl;
    }

    @Autowired
    public void setSysRoleRouteServiceImpl(SysRoleRouteServiceImpl sysRoleRouteServiceImpl) {
        ServiceImpls.sysRoleRouteServiceImpl = sysRoleRouteServiceImpl;
    }

    @Autowired
    public void setSysRouteServiceImpl(SysRouteServiceImpl sysRouteServiceImpl) {
        ServiceImpls.sysRouteServiceImpl = sysRouteServiceImpl;
    }

    @Autowired
    public void setSysUserServiceImpl(SysUserServiceImpl sysUserServiceImpl) {
        ServiceImpls.sysUserServiceImpl = sysUserServiceImpl;
    }

    @Autowired
    public void setJxcChannelServiceImpl(JxcChannelServiceImpl jxcChannelServiceImpl) {
        ServiceImpls.jxcChannelServiceImpl = jxcChannelServiceImpl;
    }

    @Autowired
    public void setJxcGoodsLableServiceImpl(JxcGoodsLableServiceImpl jxcGoodsLableServiceImpl) {
        ServiceImpls.jxcGoodsLableServiceImpl = jxcGoodsLableServiceImpl;
    }

    @Autowired
    public void setJxcGoodsServiceImpl(JxcGoodsServiceImpl jxcGoodsServiceImpl) {
        ServiceImpls.jxcGoodsServiceImpl = jxcGoodsServiceImpl;
    }

    @Autowired
    public void setJxcLableServiceImpl(JxcLableServiceImpl jxcLableServiceImpl) {
        ServiceImpls.jxcLableServiceImpl = jxcLableServiceImpl;
    }

    @Autowired
    public void setJxcPurchaseSalesServiceImpl(JxcPurchaseSalesServiceImpl jxcPurchaseSalesServiceImpl) {
        ServiceImpls.jxcPurchaseSalesServiceImpl = jxcPurchaseSalesServiceImpl;
    }

    @Autowired
    public void setJxcStockServiceImpl(JxcStockServiceImpl jxcStockServiceImpl) {
        ServiceImpls.jxcStockServiceImpl = jxcStockServiceImpl;
    }
}
