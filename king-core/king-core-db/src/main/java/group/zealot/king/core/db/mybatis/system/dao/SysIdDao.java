package group.zealot.king.core.db.mybatis.system.dao;

import group.zealot.king.core.db.mybatis.base.BaseMapper;
import group.zealot.king.core.zt.mif.entity.system.SysId;

public interface SysIdDao extends BaseMapper<SysId, Long> {
    Integer getMax();
}
