package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.core.db.mybatis.base.BaseMapper;
import group.zealot.king.core.db.mybatis.base.BaseServiceImpl;
import group.zealot.king.core.zt.mif.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.mif.service.system.SysRoleRouteService;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.mybatis.Daos.sysRoleRouteDao;


@Service
public class SysRoleRouteServiceImpl extends BaseServiceImpl<SysRoleRoute, Long> implements SysRoleRouteService {

    @Override
    protected BaseMapper<SysRoleRoute, Long> getBaseMapper() {
        return sysRoleRouteDao;
    }
}
