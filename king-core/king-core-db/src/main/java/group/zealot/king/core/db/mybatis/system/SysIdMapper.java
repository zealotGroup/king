package group.zealot.king.core.db.mybatis.system;

import group.zealot.king.core.db.mybatis.core.base.BaseDao;
import group.zealot.king.core.zt.entity.system.SysId;
import org.springframework.stereotype.Repository;

@Repository
public class SysIdMapper extends BaseDao<SysId, Long> {
    public Integer getMax() {
        return selectOne("getMax");
    }
}
