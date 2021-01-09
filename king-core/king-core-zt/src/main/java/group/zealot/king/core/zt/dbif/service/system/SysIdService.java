package group.zealot.king.core.zt.dbif.service.system;

import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.system.SysId;

public interface SysIdService extends BaseService<SysId, Long> {
    Long getId();
}
