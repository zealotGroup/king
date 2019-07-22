package group.zealot.king.core.zt.mif.entity.system;

import group.zealot.king.core.zt.mif.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 每条数据，仅有两个子段可同时存在
 * 1、sysUserId
 *  11、sysRoleDataId 此用户有哪些数据角色ID（多条）
 *  12、sysRoleRouteId 此用户有哪些路由角色ID（多条）
 * 2、sysRoleDataId
 *  21、sysDataId 此数据角色有哪些数据ID（多条）
 * 3、sysRoleRouteId
 *  31、sysRouteId 此路由角色有哪些路由ID（多条）
 */
@Getter
@Setter
public class SysAuth extends BaseEntity {
    private Long sysUserId;
    private Long sysRoleDataId;
    private Long sysDataId;
    private Long sysRoleRouteId;
    private Long sysRouteId;
}
