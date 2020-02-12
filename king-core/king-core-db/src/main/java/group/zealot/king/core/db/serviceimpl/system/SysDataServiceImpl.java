package group.zealot.king.core.db.serviceimpl.system;

import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.zt.entity.system.SysData;
import group.zealot.king.core.zt.dbif.service.system.SysDataService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.jpa.Repositorys.sysDataRepository;
import static group.zealot.king.core.db.mybatis.Mappers.sysDataMapper;


@Service
public class SysDataServiceImpl extends BaseServiceImpl<SysData, Long> implements SysDataService {

    @Override
    protected BaseMapper<SysData, Long> getBaseMapper() {
        return sysDataMapper;
    }

    @Override
    protected JpaRepository<SysData, Long> getJpaRepository() {
        return sysDataRepository;
    }
}
