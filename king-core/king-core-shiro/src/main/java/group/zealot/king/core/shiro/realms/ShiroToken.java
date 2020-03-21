package group.zealot.king.core.shiro.realms;

import group.zealot.king.base.util.StringUtil;
import lombok.Data;
import lombok.Getter;
import org.apache.shiro.authc.AuthenticationToken;

@Getter
public class ShiroToken implements AuthenticationToken {
    private String captcha;
    private String username;
    private byte[] password;
    private String openid;

    public ShiroToken(String username, byte[] password) {
        this(username, password, null);
    }

    public ShiroToken(String username, byte[] password, String captcha) {
        this.username = username;
        this.password = password;
        this.captcha = captcha;
    }

    public ShiroToken(String openid) {
        this.openid = openid;
    }

    @Override
    public Object getPrincipal() {
        if (StringUtil.isNotEmpty(openid)) {
            return openid;
        } else {
            return this.username;
        }

    }

    @Override
    public Object getCredentials() {
        if (StringUtil.isNotEmpty(openid)) {
            return openid;
        } else {
            return this.password;
        }
    }
}