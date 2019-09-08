package group.zealot.king.core.zt.mif.service.system;

import group.zealot.king.core.zt.dubbo.MyDubboService;
import group.zealot.king.core.zt.mif.entity.system.SysId;
import group.zealot.king.core.zt.mif.service.BaseService;

@MyDubboService
public interface SysIdService extends BaseService<SysId> {
    Long getId();
}
