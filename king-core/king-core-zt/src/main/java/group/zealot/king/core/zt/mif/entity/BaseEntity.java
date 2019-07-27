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

    private Integer isDelete;//1 已删除 0 未删除

    /**
     * 非数据库字段
     */
    private String createUserName;
    private String lastUpdateUserName;
}
