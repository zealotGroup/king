package group.zealot.king.core.zt.mybatis.system.service;

import group.zealot.king.core.zt.mybatis.system.entity.SysUser;

public interface SysUserService {
    SysUser getByUsernameAndPassword(String username, String password);

    SysUser getByUsername(String username);
}
