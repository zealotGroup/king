package group.zealot.king.core.db.mybatis;

import group.zealot.king.core.db.mybatis.system.dao.SysIdDao;
import group.zealot.king.core.db.mybatis.system.dao.SysUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Daos {
    public static SysIdDao sysIdDao;
    public static SysUserDao sysUserDao;

    @Autowired(required = false)
    public void setSysIdDao(SysIdDao sysIdDao) {
        Daos.sysIdDao = sysIdDao;
    }

    @Autowired(required = false)
    public void setSysUserDao(SysUserDao sysUserDao) {
        Daos.sysUserDao = sysUserDao;
    }
}
