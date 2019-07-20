package group.zealot.king.core.zt.mif.service.system;

import group.zealot.king.core.zt.dubbo.MyDubboService;
import group.zealot.king.core.zt.mif.entity.system.SysUser;

@MyDubboService
public interface SysRouteService {
    SysUser getByUsernameAndPassword(String username, String password);

    SysUser getByUsername(String username);

    void updatePassword(String username, String newPassword);
}
