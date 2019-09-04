package group.zealot.king.core.zt.mif.entity.system;

import com.alibaba.fastjson.JSONArray;
import group.zealot.king.core.zt.mif.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysRoute extends BaseEntity {
    private String name;
    private Long fId;
    /**
     * 非数据库字段
     */
    private String fName;
}
