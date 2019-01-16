package group.zealot.king.core.shiro;

public class ShiroException extends RuntimeException {

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
