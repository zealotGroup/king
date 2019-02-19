package group.zealot.king.base;

public enum ServiceCode {
    REQUEST_ERROR(100, "错误请求"),
    SUCCESS(200, "处理成功"),
    NEED_LOGIN(202,"需要认证"),
    NEED_REQUEST_ID(203,"需要授权的请求序号"),
    EXCEPTION(300, "异常"),
    EXCEPTION_RUNNTIME(301, "运行时异常"),
    NOT_FOUND(404, "服务不存在"),
    ;

    private final int code;
    private final String msg;

    ServiceCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }
}
