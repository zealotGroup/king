package group.zealot.king.core.shiro.realm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ShiroUser implements Serializable {
    private static final long serialVersionUID = 117080078237313815L;
    private Long userId;//对应数据库用户表主键
    private String username;
    private String password;
    private String salt;
}