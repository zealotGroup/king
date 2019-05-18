package group.zealot.king.core.zt.mif.entity.system;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUser {
    private Long id;
    private String username;
    @JSONField(serialize=false)
    private String password;
}
