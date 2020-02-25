package group.zealot.king.core.db.mybatis;

import group.zealot.king.core.db.mybatis.admin.AdminLableMapper;
import group.zealot.king.core.db.mybatis.jxc.*;
import group.zealot.king.core.db.mybatis.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
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

    @Autowired
    public void setSysAuthMapper(SysAuthMapper sysAuthMapper) {
        Mappers.sysAuthMapper = sysAuthMapper;
    }

    @Autowired
    public void setSysDataMapper(SysDataMapper sysDataMapper) {
        Mappers.sysDataMapper = sysDataMapper;
    }

    @Autowired
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        Mappers.sysUserMapper = sysUserMapper;
    }

    @Autowired
    public void setSysRoleDataMapper(SysRoleDataMapper sysRoleDataMapper) {
        Mappers.sysRoleDataMapper = sysRoleDataMapper;
    }

    @Autowired
    public void setSysRoleRouteMapper(SysRoleRouteMapper sysRoleRouteMapper) {
        Mappers.sysRoleRouteMapper = sysRoleRouteMapper;
    }

    @Autowired
    public void setSysRouteMapper(SysRouteMapper sysRouteMapper) {
        Mappers.sysRouteMapper = sysRouteMapper;
    }

    @Autowired
    public void setSysIdMapper(SysIdMapper sysIdMapper) {
        Mappers.sysIdMapper = sysIdMapper;
    }

    @Autowired
    public void setJxcCustMapper(JxcCustMapper jxcCustMapper) {
        Mappers.jxcCustMapper = jxcCustMapper;
    }

    @Autowired
    public void setJxcGoodsLableMapper(JxcGoodsLableMapper jxcGoodsLableMapper) {
        Mappers.jxcGoodsLableMapper = jxcGoodsLableMapper;
    }

    @Autowired
    public void setJxcGoodsMapper(JxcGoodsMapper jxcGoodsMapper) {
        Mappers.jxcGoodsMapper = jxcGoodsMapper;
    }

    @Autowired
    public void setAdminLableMapper(AdminLableMapper adminLableMapper) {
        Mappers.adminLableMapper = adminLableMapper;
    }

    @Autowired
    public void setJxcPurchaseMapper(JxcPurchaseMapper jxcPurchaseMapper) {
        Mappers.jxcPurchaseMapper = jxcPurchaseMapper;
    }

    @Autowired
    public void setJxcStockMapper(JxcStockMapper jxcStockMapper) {
        Mappers.jxcStockMapper = jxcStockMapper;
    }

    @Autowired
    public void setJxcGoodsPictureMapper(JxcGoodsPictureMapper jxcGoodsPictureMapper) {
        Mappers.jxcGoodsPictureMapper = jxcGoodsPictureMapper;
    }
}
