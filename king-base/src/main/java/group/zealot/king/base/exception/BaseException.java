package group.zealot.king.base.exception;

import group.zealot.king.base.ServiceCode;

public class BaseException extends Exception {


    private String code;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }


    public String getCode() {
        return code;
    }
}