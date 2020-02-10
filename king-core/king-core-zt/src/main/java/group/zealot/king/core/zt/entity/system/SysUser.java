package group.zealot.king.core.zt.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table
public class SysUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 200)
    private String username;
    @Column(length = 200)
    @JSONField(serialize = false)
    private String password;
    @Column(length = 200)
    private String level;
    @Column(length = 200)
    private String status;

    @Column(updatable = false)
    private LocalDateTime insertTime;

    @PrePersist
    protected void prePersist() {
        this.insertTime = LocalDateTime.now();
    }

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
