package group.zealot.king.base.exception;

public class BaseRuntimeException extends RuntimeException {
    private String code;

    public BaseRuntimeException() {
        super();
    }

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }
}
