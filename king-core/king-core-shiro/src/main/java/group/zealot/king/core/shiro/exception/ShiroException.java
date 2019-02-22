package group.zealot.king.core.shiro.exception;

import org.apache.shiro.authc.AuthenticationException;

public class ShiroException extends AuthenticationException {

    public ShiroException() {
        super();
    }

    public ShiroException(String message) {
        super(message);
    }

    public ShiroException(Throwable cause) {
        super(cause);
    }

    public ShiroException(String message, Throwable cause) {
        super(message, cause);
    }

}
