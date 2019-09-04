package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.core.db.mybatis.base.BaseDao;
import group.zealot.king.core.db.mybatis.base.BaseServiceAbs;
import group.zealot.king.core.zt.mif.entity.system.SysData;
import group.zealot.king.core.zt.mif.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.mif.service.system.SysRoleRouteService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static group.zealot.king.core.db.mybatis.Daos.sysRoleRouteDao;


@Service
public class SysRoleRouteServiceImpl extends BaseServiceAbs<SysRoleRoute, Long> implements SysRoleRouteService {

    @Override
    protected BaseDao<SysRoleRoute, Long> getBaseDao() {
        return sysRoleRouteDao;
    }

    @Override
    protected SysRoleRoute getE(Long primaryKey, Long userId) {
        SysRoleRoute vo = new SysRoleRoute();
        vo.setLastUpdateTime(LocalDateTime.now());
        vo.setLastUpdateUserId(userId);
        vo.setId(primaryKey);
        return vo;
    }
}
