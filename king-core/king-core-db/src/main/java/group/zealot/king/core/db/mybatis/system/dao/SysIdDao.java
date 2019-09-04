package group.zealot.king.core.db.mybatis.system.dao;

import group.zealot.king.core.db.mybatis.base.BaseDao;
import group.zealot.king.core.zt.mif.entity.system.SysId;

public interface SysIdDao extends BaseDao<SysId, Long> {
    Integer getMax();
}
