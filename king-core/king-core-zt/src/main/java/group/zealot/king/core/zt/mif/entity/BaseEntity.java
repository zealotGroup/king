package group.zealot.king.core.zt.mif.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class BaseEntity implements Serializable {
    private Long id;
    private Long createUserId;
    private LocalDateTime createTime;
    private Long lastUpdateUserId;
    private LocalDateTime lastUpdateTime;
    private String remark;
}
