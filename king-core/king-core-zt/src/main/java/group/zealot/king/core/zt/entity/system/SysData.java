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
public class SysData extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 200)
    private String name;
    @Column(length = 20)
    private Long fId;
    @Column(updatable = false)
    private LocalDateTime insertTime;

    @PrePersist
    protected void prePersist() {
        this.insertTime = LocalDateTime.now();
    }
}
