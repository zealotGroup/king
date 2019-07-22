package group.zealot.king.base.exception;

import group.zealot.king.base.ServiceCode;

public class BaseRuntimeException extends RuntimeException {
    private String code;
    private ServiceCode serviceCode;

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

    public BaseRuntimeException(ServiceCode serviceCode) {
        super();
        this.serviceCode = serviceCode;
    }

    public BaseRuntimeException(ServiceCode serviceCode, String message) {
        super(message);
        this.serviceCode = serviceCode;
    }

    public String getCode() {
        return code;
    }

    public ServiceCode getServiceCode() {
        return serviceCode;
    }
}
