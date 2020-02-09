package group.zealot.king.core.db.serviceimpl.system.serviceImpl;

import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.zt.entity.system.SysRoute;
import group.zealot.king.core.zt.dbif.service.system.SysRouteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.mybatis.Mappers.sysRouteDaoMapper;
import static group.zealot.king.core.db.jpa.Repositorys.*;


@Service
public class SysRouteServiceImpl extends BaseServiceImpl<SysRoute, Long> implements SysRouteService {

    @Override
    protected BaseMapper<SysRoute, Long> getBaseMapper() {
        return sysRouteDaoMapper;
    }

    @Override
    protected JpaRepository<SysRoute, Long> getJpaRepository() {
        return sysRouteRepository;
    }
}
