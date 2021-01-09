package group.zealot.king.core.zt.dbif.service.system;

import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.system.SysUser;


public interface SysUserService extends BaseService<SysUser, Long> {
    SysUser getByUsername(String username);

    String getNewPassword(String username, byte[] password);

    SysUser insert(String username, byte[] password, String status, String level, Long roleDataId, Long roleRouteId);

    void deleteSysUser(Long id);

    SysUser update(Long id, String status, String level, Long roleDataId, Long roleRouteId);
}
