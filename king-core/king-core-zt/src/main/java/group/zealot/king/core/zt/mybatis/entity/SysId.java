package group.zealot.king.core.zt.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SysId {
    private Integer id;
    private LocalDateTime insertTime;
}
