package group.zealot.king.core.zt.mybatis.dao.mapper;

import group.zealot.king.core.zt.mybatis.base.BaseDao;
import group.zealot.king.core.zt.mybatis.dao.SysIdDao;
import group.zealot.king.core.zt.mybatis.entity.SysId;
import org.springframework.stereotype.Repository;

@Repository
public class SysIdDaoMapper extends BaseDao<SysId, Long> implements SysIdDao {

    @Override
    public Integer getMax() {
        return selectOne("getMax");
    }
}
