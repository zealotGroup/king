package group.zealot.king.core.db.mybatis;

import group.zealot.king.core.db.mybatis.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mappers {
    public static SysAuthDaoMapper sysAuthDaoMapper;
    public static SysDataDaoMapper sysDataDaoMapper;
    public static SysUserDaoMapper sysUserDaoMapper;
    public static SysRoleDataDaoMapper sysRoleDataDaoMapper;
    public static SysRoleRouteDaoMapper sysRoleRouteDaoMapper;
    public static SysRouteDaoMapper sysRouteDaoMapper;
    public static SysIdDaoMapper sysIdDaoMapper;

    @Autowired(required = false)
    public void setSysAuthDaoMapper(SysAuthDaoMapper sysAuthDaoMapper) {
        Mappers.sysAuthDaoMapper = sysAuthDaoMapper;
    }

    @Autowired(required = false)
    public void setSysDataDaoMapper(SysDataDaoMapper sysDataDaoMapper) {
        Mappers.sysDataDaoMapper = sysDataDaoMapper;
    }

    @Autowired(required = false)
    public void setSysUserDaoMapper(SysUserDaoMapper sysUserDaoMapper) {
        Mappers.sysUserDaoMapper = sysUserDaoMapper;
    }

    @Autowired(required = false)
    public void setSysRoleDataDaoMapper(SysRoleDataDaoMapper sysRoleDataDaoMapper) {
        Mappers.sysRoleDataDaoMapper = sysRoleDataDaoMapper;
    }

    @Autowired(required = false)
    public void setSysRoleRouteDaoMapper(SysRoleRouteDaoMapper sysRoleRouteDaoMapper) {
        Mappers.sysRoleRouteDaoMapper = sysRoleRouteDaoMapper;
    }

    @Autowired(required = false)
    public void setSysRouteDaoMapper(SysRouteDaoMapper sysRouteDaoMapper) {
        Mappers.sysRouteDaoMapper = sysRouteDaoMapper;
    }

    @Autowired(required = false)
    public void setSysIdDaoMapper(SysIdDaoMapper sysIdDaoMapper) {
        Mappers.sysIdDaoMapper = sysIdDaoMapper;
    }
}
