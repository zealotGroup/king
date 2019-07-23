package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.core.db.mybatis.base.BaseMapper;
import group.zealot.king.core.db.mybatis.base.BaseServiceImpl;
import group.zealot.king.core.zt.mif.entity.system.SysRoute;
import group.zealot.king.core.zt.mif.service.system.SysRouteService;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.mybatis.Daos.sysRouteDao;


@Service
public class SysRouteServiceImpl extends BaseServiceImpl<SysRoute, Long> implements SysRouteService {

    @Override
    protected BaseMapper<SysRoute, Long> getBaseMapper() {
        return sysRouteDao;
    }
}
