package group.zealot.king.core.shiro;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShiroUser {
    private Long userId;//对应数据库用户表主键
    private String username;
    private String password;
    private String salt;
}