package group.zealot.king.core.zt.mybatis.system.dao;

import group.zealot.king.core.zt.mybatis.base.BaseMapper;
import group.zealot.king.core.zt.mybatis.system.entity.SysId;

public interface SysIdDao extends BaseMapper<SysId, Long> {
    Integer getMax();
}
