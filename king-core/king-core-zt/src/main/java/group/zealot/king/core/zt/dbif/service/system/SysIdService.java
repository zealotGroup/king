package group.zealot.king.core.zt.dbif.service.system;

import group.zealot.king.core.zt.entity.system.SysId;
import group.zealot.king.core.zt.dbif.service.BaseService;

public interface SysIdService extends BaseService<SysId, Long> {
    Long getId();
}
