package group.zealot.king.core.zt.entity.system;

import group.zealot.king.core.zt.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
public class SysRoleData extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String name;
    @Column(updatable = false)
    private LocalDateTime insertTime;

    @PrePersist
    protected void prePersist() {
        this.insertTime = LocalDateTime.now();
    }
}
