package group.zealot.king.core.zt.mif.entity.system;

import group.zealot.king.core.zt.mif.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SysId extends BaseEntity {
    private Integer id;
    private LocalDateTime insertTime;
}
