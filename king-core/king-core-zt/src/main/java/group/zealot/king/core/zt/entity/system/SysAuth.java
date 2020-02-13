package group.zealot.king.core.zt.entity.system;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 每条数据，仅有两个子段可同时存在
 * 1、sysUserId
 * 11、sysRoleDataId 此用户有哪些数据角色ID（多条）
 * 12、sysRoleRouteId 此用户有哪些路由角色ID（多条）
 * 2、sysRoleDataId
 * 21、sysDataId 此数据角色有哪些数据ID（多条）
 * 3、sysRoleRouteId
 * 31、sysRouteId 此路由角色有哪些路由ID（多条）
 */
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"sysUserId", "sysRoleDataId"}),
        @UniqueConstraint(columnNames = {"sysUserId", "sysRoleRouteId"}),
        @UniqueConstraint(columnNames = {"sysRouteId", "sysRoleRouteId"}),
        @UniqueConstraint(columnNames = {"sysDataId", "sysRoleRouteId"})})
public class SysAuth extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private Long sysUserId;
    @Column(length = 20)
    private Long sysRoleDataId;
    @Column(length = 20)
    private Long sysDataId;
    @Column(length = 20)
    private Long sysRoleRouteId;
    @Column(length = 20)
    private Long sysRouteId;
}
