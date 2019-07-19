package group.zealot.king.core.db.mybatis;

import group.zealot.king.core.db.mybatis.system.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Daos {
    public static SysIdDao sysIdDao;
    public static SysUserDao sysUserDao;
    public static SysAuthDao sysAuthDao;
    public static SysRoleDataDao sysRoleDataDao;
    public static SysRoleRouteDao sysRoleRouteDao;
    public static SysRouteDao sysRouteDao;
    public static SysDataDao sysDataDao;

    @Autowired(required = false)
    public void setSysAuthDao(SysAuthDao sysAuthDao) {
        Daos.sysAuthDao = sysAuthDao;
    }

    @Autowired(required = false)
    public void setSysIdDao(SysIdDao sysIdDao) {
        Daos.sysIdDao = sysIdDao;
    }

    @Autowired(required = false)
    public void setSysUserDao(SysUserDao sysUserDao) {
        Daos.sysUserDao = sysUserDao;
    }
}
