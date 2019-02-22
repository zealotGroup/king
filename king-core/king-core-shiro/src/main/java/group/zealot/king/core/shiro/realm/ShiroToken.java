package group.zealot.king.core.shiro.realm;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.UsernamePasswordToken;

@Getter
@Setter
public class ShiroToken extends UsernamePasswordToken {
    private String captcha;

    public ShiroToken(String username, char[] password) {
        this(username, password, false, null);
    }

    public ShiroToken(String username, char[] password, String captcha) {
        this(username, password, false, captcha);
    }

    public ShiroToken(String username, char[] password, boolean remeberMe, String captcha) {
        super(username, password, remeberMe);
        this.captcha = captcha;
    }

    @Override
    public ShiroUser getPrincipal() {
        return (ShiroUser) super.getPrincipal();
    }
}
