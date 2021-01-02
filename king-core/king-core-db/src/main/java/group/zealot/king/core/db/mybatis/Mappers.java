package group.zealot.king.core.db.mybatis;

import group.zealot.king.core.db.mybatis.admin.AdminLableMapper;
import group.zealot.king.core.db.mybatis.jxc.*;
import group.zealot.king.core.db.mybatis.system.*;
import group.zealot.king.core.zt.aop.ZTComponent;

@ZTComponent
public class Mappers {
    public static SysAuthMapper sysAuthMapper;
    public static SysDataMapper sysDataMapper;
    public static SysUserMapper sysUserMapper;
    public static SysRoleDataMapper sysRoleDataMapper;
    public static SysRoleRouteMapper sysRoleRouteMapper;
    public static SysRouteMapper sysRouteMapper;
    public static SysIdMapper sysIdMapper;

    public static JxcCustMapper jxcCustMapper;
    public static JxcGoodsLableMapper jxcGoodsLableMapper;
    public static JxcGoodsMapper jxcGoodsMapper;
    public static AdminLableMapper adminLableMapper;
    public static JxcPurchaseMapper jxcPurchaseMapper;
    public static JxcStockMapper jxcStockMapper;
    public static JxcGoodsPictureMapper jxcGoodsPictureMapper;
}
