package group.zealot.king.core.db.mybatis.system.dao.mapper;

import group.zealot.king.core.db.mybatis.base.BaseDaoImpl;
import group.zealot.king.core.db.mybatis.system.dao.SysIdDao;
import group.zealot.king.core.zt.mif.entity.system.SysId;
import org.springframework.stereotype.Repository;

@Repository
public class SysIdDaoMapper extends BaseDaoImpl<SysId, Long> implements SysIdDao {

    @Override
    public Integer getMax() {
        return selectOne("getMax");
    }
}
