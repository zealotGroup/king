package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.core.db.mybatis.base.BaseDao;
import group.zealot.king.core.db.mybatis.base.BaseServiceAbs;
import group.zealot.king.core.zt.mif.entity.system.SysRoute;
import group.zealot.king.core.zt.mif.service.system.SysRouteService;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.mybatis.Daos.sysRouteDao;


@Service
public class SysRouteServiceImpl extends BaseServiceAbs<SysRoute, Long> implements SysRouteService {

    @Override
    protected BaseDao<SysRoute, Long> getBaseDao() {
        return sysRouteDao;
    }

    @Override
    public void formater(SysRoute vo) {
        if (vo.getFId() != null) {
            vo.setFName(sysRouteDao.getById(vo.getFId()).getName());
        }
    }

    @Override
    protected SysRoute getE(Long primaryKey ) {
        SysRoute vo = new SysRoute();
        vo.setId(primaryKey);
        return vo;
    }
}
