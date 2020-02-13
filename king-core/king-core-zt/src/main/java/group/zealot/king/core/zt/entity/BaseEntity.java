package group.zealot.king.core.zt.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zealot
 * @date 2020/2/10 10:24
 */
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {
    @Column(updatable = false)
    private LocalDateTime insertTime;
    @Column(insertable = false)
    private LocalDateTime updateTime;

    @PrePersist
    protected void prePersist() {
        this.insertTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}
