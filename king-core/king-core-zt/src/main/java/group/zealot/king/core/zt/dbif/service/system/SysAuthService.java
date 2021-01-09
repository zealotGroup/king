package group.zealot.king.core.zt.dbif.service.system;

import com.alibaba.fastjson.JSONArray;
import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.system.*;

import java.util.List;

public interface SysAuthService extends BaseService<SysAuth, Long> {
    SysAuth getSysAuthRoleData(Long sysUserId);

    SysAuth getSysAuthRoleRoute(Long sysUserId);

    /**
     * 根据 用户ID 获取 所有的 数据角色
     *
     * @param sysUserId 用户ID
     * @return List<SysRoleData> 该用户 所对应的 数据角色
     */
    SysRoleData getSysRoleData(Long sysUserId);

    /**
     * 根据 用户ID 获取 所有的 路由角色
     *
     * @param sysUserId 用户ID
     * @return List<SysRoleRoute> 该用户 所对应的 路由角色
     */
    SysRoleRoute getSysRoleRoute(Long sysUserId);

    /**
     * 根据 路由角色ID 获取 所包含的 路由
     *
     * @param sysRoleRouteId 路由角色ID
     * @return List<SysRoute> 该路由角色 所包含的 路由
     */
    List<SysRoute> getSysRoute(Long sysRoleRouteId);

    /**
     * 根据 数据角色ID 获取 所有的 数据
     *
     * @param sysRoleDataId 数据角色ID
     * @return List<SysData> 该数据角色 所包含的 数据
     */
    List<SysData> getSysData(Long sysRoleDataId);

    /**
     * 根据 用户ID 获取 JSONArray形式的 路由信息（前台接受，配置菜单和授权）
     *
     * @param sysUserId 用户ID
     * @return JSONArray 路由信息
     */
    JSONArray getRoute(Long sysUserId);
}
