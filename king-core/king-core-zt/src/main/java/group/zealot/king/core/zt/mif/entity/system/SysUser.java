package group.zealot.king.core.zt.mif.entity.system;

import group.zealot.king.core.zt.mif.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
public class SysUser extends BaseEntity {
    private String username;
    private String password;
    private String level;
    private String status;
    private LocalDateTime lastLoginTime;
    /**
     * 非数据库字段
     */
    private Long roleRouteId;
    private String roleRouteName;
    private Long roleDataId;
    private String roleDataName;
}
