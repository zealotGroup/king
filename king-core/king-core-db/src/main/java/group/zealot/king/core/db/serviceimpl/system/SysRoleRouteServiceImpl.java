package group.zealot.king.core.db.serviceimpl.system;

import group.zealot.king.core.db.base.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.system.SysRoleRouteService;
import group.zealot.king.core.zt.entity.system.SysAuth;
import group.zealot.king.core.zt.entity.system.SysRoleRoute;
import org.springframework.stereotype.Service;

import java.util.List;

import static group.zealot.king.core.db.serviceimpl.ServiceImpls.sysAuthServiceImpl;

@Service
public class SysRoleRouteServiceImpl extends BaseServiceImpl<SysRoleRoute, Long> implements SysRoleRouteService {

    @Override
    public SysRoleRoute insert(String name, List<Long> routeList) {
        SysRoleRoute sysRoleRoute = new SysRoleRoute();
        sysRoleRoute.setName(name);
        sysRoleRoute = insert(sysRoleRoute);
        Long sysRoleRouteId = sysRoleRoute.getId();
        routeList.forEach(sysRouteId -> {
            SysAuth sysAuth = new SysAuth();
            sysAuth.setSysRouteId(sysRouteId);
            sysAuth.setSysRoleRouteId(sysRoleRouteId);
            sysAuthServiceImpl.insert(sysAuth);
        });
        return sysRoleRoute;
    }

    @Override
    public SysRoleRoute update(Long id, String name, List<Long> routeList) {
        SysRoleRoute sysRoleRoute = getById(id);
        sysRoleRoute.setName(name);
        update(sysRoleRoute);

        SysAuth vo = new SysAuth();
        vo.setSysRoleRouteId(id);
        List<SysAuth> sysAuthList = sysAuthServiceImpl.getList(vo);
        sysAuthList.forEach(sysAuth -> {
            if (sysAuth.getSysRouteId() != null) {
                if (routeList.contains(sysAuth.getSysRouteId())) {
                    routeList.remove(sysAuth.getSysRouteId());
                } else {
                    sysAuthServiceImpl.deleteById(sysAuth.getId());
                }
            }
        });

        routeList.forEach(sysRouteId -> {
            SysAuth sysAuth = new SysAuth();
            sysAuth.setSysRouteId(sysRouteId);
            sysAuth.setSysRoleRouteId(id);
            sysAuthServiceImpl.insert(sysAuth);
        });
        return sysRoleRoute;
    }
}
