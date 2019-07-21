package group.zealot.king.core.zt.mif.service.system;

import group.zealot.king.core.zt.dubbo.MyDubboService;
import group.zealot.king.core.zt.mif.entity.system.SysRoute;
import group.zealot.king.core.zt.mif.entity.system.SysUser;

@MyDubboService
public interface SysRouteService {
    SysRoute getById(Long id);
}
