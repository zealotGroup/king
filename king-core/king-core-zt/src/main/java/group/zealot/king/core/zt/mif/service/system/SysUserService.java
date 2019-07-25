package group.zealot.king.core.zt.mif.service.system;

import group.zealot.king.core.zt.dubbo.MyDubboService;
import group.zealot.king.core.zt.mif.entity.system.SysUser;
import group.zealot.king.core.zt.mif.service.BaseService;


@MyDubboService
public interface SysUserService extends BaseService<SysUser, Long> {
    SysUser getByUsernameAndPassword(String username, byte[] password);

    SysUser getByUsername(String username);

    void updatePassword(String username, byte[] newPassword);

    SysUser insert(String username, byte[] password, String status, String level, String remark, Long userId);
}
