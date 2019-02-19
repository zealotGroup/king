package group.zealot.king.core.zt.mybatis.system.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUser {
    private Long id;
    private String username;
    private String password;
}
