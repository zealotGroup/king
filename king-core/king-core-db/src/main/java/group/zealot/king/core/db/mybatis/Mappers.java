package group.zealot.king.core.db.mybatis;

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
    public static JxcChannelMapper jxcChannelMapper;
    public static JxcGoodsLableMapper jxcGoodsLableMapper;
    public static JxcGoodsMapper jxcGoodsMapper;
    public static JxcLableMapper jxcLableMapper;
    public static JxcPurchaseSalesMapper jxcPurchaseSalesMapper;
    public static JxcStockMapper jxcStockMapper;

    @Autowired(required = false)
    public static void setSysAuthMapper(SysAuthMapper sysAuthMapper) {
        Mappers.sysAuthMapper = sysAuthMapper;
    }

    @Autowired(required = false)
    public void setSysDataMapper(SysDataMapper sysDataMapper) {
        Mappers.sysDataMapper = sysDataMapper;
    }

    @Autowired(required = false)
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        Mappers.sysUserMapper = sysUserMapper;
    }

    @Autowired(required = false)
    public void setSysRoleDataMapper(SysRoleDataMapper sysRoleDataMapper) {
        Mappers.sysRoleDataMapper = sysRoleDataMapper;
    }

    @Autowired(required = false)
    public void setSysRoleRouteMapper(SysRoleRouteMapper sysRoleRouteMapper) {
        Mappers.sysRoleRouteMapper = sysRoleRouteMapper;
    }

    @Autowired(required = false)
    public void setSysRouteMapper(SysRouteMapper sysRouteMapper) {
        Mappers.sysRouteMapper = sysRouteMapper;
    }

    @Autowired(required = false)
    public void setSysIdMapper(SysIdMapper sysIdMapper) {
        Mappers.sysIdMapper = sysIdMapper;
    }

    @Autowired(required = false)
    public void setJxcChannelMapper(JxcChannelMapper jxcChannelMapper) {
        Mappers.jxcChannelMapper = jxcChannelMapper;
    }

    @Autowired(required = false)
    public void setJxcGoodsLableMapper(JxcGoodsLableMapper jxcGoodsLableMapper) {
        Mappers.jxcGoodsLableMapper = jxcGoodsLableMapper;
    }

    @Autowired(required = false)
    public void setJxcGoodsMapper(JxcGoodsMapper jxcGoodsMapper) {
        Mappers.jxcGoodsMapper = jxcGoodsMapper;
    }

    @Autowired(required = false)
    public void setJxcLableMapper(JxcLableMapper jxcLableMapper) {
        Mappers.jxcLableMapper = jxcLableMapper;
    }

    @Autowired(required = false)
    public void setJxcPurchaseSalesMapper(JxcPurchaseSalesMapper jxcPurchaseSalesMapper) {
        Mappers.jxcPurchaseSalesMapper = jxcPurchaseSalesMapper;
    }

    @Autowired(required = false)
    public void setJxcStockMapper(JxcStockMapper jxcStockMapper) {
        Mappers.jxcStockMapper = jxcStockMapper;
    }
}
