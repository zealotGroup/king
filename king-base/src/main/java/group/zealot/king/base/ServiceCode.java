package group.zealot.king.base;

public enum ServiceCode {
    REQUEST_ERROR(100, "失败，请求信息错误"),
    REQUEST_METHOD_NOT_ALLOWED(101, "失败，请求方法不允许"),
    SUCCESS(200, "成功"),
    NO_USER(201, "失败，用户不存在"),
    NEED_LOGIN(202, "失败，需要认证"),
    NEED_REQUEST_ID(203, "失败，需要授权的请求序号"),
    PARAM_IS_EMPTY(205, "必传参数为空"),
    DO_ERROR(250, "失败，业务处理失败"),
    EXCEPTION(300, "失败"),
    EXCEPTION_RUNNTIME(301, "失败，运行时出现异常"),
    NOT_FOUND(404, "失败，服务不存在"),
    REQUEST_HTTP_ERROR(999, "服务请求外部资源异常"),

    DECRYPT_ERROR(888, "解密算法异常"),
    PARAM_IS_INVALID(887, "数据非法");
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
