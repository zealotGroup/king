package group.zealot.king.core.zt.dbif.service.system;

import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.system.SysRoleRoute;

import java.util.List;

public interface SysRoleRouteService extends BaseService<SysRoleRoute, Long> {
    SysRoleRoute insert(String name, List<Long> routeList);

    SysRoleRoute update(Long id, String name, List<Long> routeList);
}
