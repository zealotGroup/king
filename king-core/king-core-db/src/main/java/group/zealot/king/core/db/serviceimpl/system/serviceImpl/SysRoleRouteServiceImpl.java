package group.zealot.king.core.db.serviceimpl.system.serviceImpl;

import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.zt.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.dbif.service.system.SysRoleRouteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.mybatis.Mappers.sysRoleRouteDaoMapper;
import static group.zealot.king.core.db.jpa.Repositorys.*;


@Service
public class SysRoleRouteServiceImpl extends BaseServiceImpl<SysRoleRoute, Long> implements SysRoleRouteService {

    @Override
    protected BaseMapper<SysRoleRoute, Long> getBaseMapper() {
        return sysRoleRouteDaoMapper;
    }

    @Override
    protected JpaRepository<SysRoleRoute, Long> getJpaRepository() {
        return sysRoleRouteRepository;
    }
}
