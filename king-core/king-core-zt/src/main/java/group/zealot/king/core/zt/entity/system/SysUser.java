package group.zealot.king.core.zt.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class SysUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String username;
    @Column(length = 200)
    @JSONField(serialize = false)
    private String password;
    @Column(length = 200)
    private String level;
    @Column(length = 200)
    private String status;

    /**
     * 非数据库字段
     */
    @Transient
    private Long roleRouteId;
    @Transient
    private String roleRouteName;
    @Transient
    private Long roleDataId;
    @Transient
    private String roleDataName;
}
