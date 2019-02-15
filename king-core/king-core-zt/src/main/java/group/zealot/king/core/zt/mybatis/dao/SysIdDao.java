package group.zealot.king.core.zt.mybatis.dao;

import group.zealot.king.core.zt.mybatis.BaseDao;
import group.zealot.king.core.zt.mybatis.entity.SysId;
import group.zealot.king.core.zt.mybatis.mapper.SysIdMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SysIdDao extends BaseDao<SysId, Long> implements SysIdMapper {

    @Override
    public String getMapperNamesapce() {
        return SysIdMapper.class.getName();
    }

    @Override
    public void insert(SysId sysId) {
        getSqlSession().insert(getMapperNamesapce() + ".insert", sysId);
    }
}
