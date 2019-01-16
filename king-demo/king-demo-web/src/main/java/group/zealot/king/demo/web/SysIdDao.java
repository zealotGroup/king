package group.zealot.king.demo.web;

import group.zealot.king.core.mybatis.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class SysIdDao extends BaseDao<SysId, Long> implements SysIdMapper {

    @Override
    public String getMapperNamesapce() {
        return SysIdMapper.class.getName();
    }

    public void insert(SysId sysId) {
        getSqlSession().insert(getMapperNamesapce() + ".insert", sysId);
    }
}
