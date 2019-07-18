package group.zealot.king.core.zt.mif.service.system;

import group.zealot.king.core.zt.dubbo.MyDubboService;
import group.zealot.king.core.zt.mif.entity.system.SysData;
import group.zealot.king.core.zt.mif.entity.system.SysRoleData;
import group.zealot.king.core.zt.mif.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.mif.entity.system.SysRoute;

import java.util.List;

@MyDubboService
public interface SysAuthService {

    /**
     * 根据 用户ID 获取 所有的 数据角色
     *
     * @param sysUserId 用户ID
     * @return List<SysRoleData> 该用户 所包含的 数据角色
     */
    List<SysRoleData> getRoleData(Long sysUserId);

    /**
     * 根据 用户ID 获取 所有的 路由角色
     *
     * @param sysUserId 用户ID
     * @return List<SysRoleRoute> 该用户 所包含的 路由角色
     */
    List<SysRoleRoute> getRoleRoute(Long sysUserId);

    /**
     * 根据 路由角色ID 获取 所有的 路由
     *
     * @param sysRoleRouteId 路由角色ID
     * @return List<SysRoute> 该路由角色 所包含的 路由
     */
    List<SysRoute> getRoute(Long sysRoleRouteId);

    /**
     * 根据 数据角色ID 获取 所有的 数据
     *
     * @param sysRoleDataId 数据角色ID
     * @return List<SysData> 该数据角色 所包含的 数据
     */
    List<SysData> getData(Long sysRoleDataId);

}
