package group.zealot.king.base;

public enum ServiceCode {
    REQUEST_ERROR(100, "失败，请求信息错误"),
    SUCCESS(200, "成功"),
    NEED_LOGIN(202,"失败，需要认证"),
    NEED_REQUEST_ID(203,"失败，需要授权的请求序号"),
    EXCEPTION(300, "失败"),
    EXCEPTION_RUNNTIME(301, "失败，运行时出现异常"),
    NOT_FOUND(404, "失败，服务不存在"),
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
