package group.zealot.king.core.zt.mybatis.system.entity;

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
