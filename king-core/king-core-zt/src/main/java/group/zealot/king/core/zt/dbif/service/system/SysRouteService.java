package group.zealot.king.core.zt.dbif.service.system;

import com.alibaba.fastjson.JSONArray;
import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.system.SysRoute;

public interface SysRouteService extends BaseService<SysRoute, Long> {

    /**
     * 获取所有路由权限信息（JSONArray形式的、树形），路由角色ID所包含的节点，标记checked=true
     *
     * @param sysRoleRouteId 路由角色ID
     * @return JSONArray 路由信息
     */
    JSONArray getRouteTree(Long sysRoleRouteId);
}
