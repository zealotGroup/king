package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ResultTemple {
    @Getter
    protected final ResultJson resultJson;

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public ResultTemple() {
        this(null, null);
    }

    public ResultTemple(HttpServletRequest request) {
        this(request, null);
    }

    public ResultTemple(HttpServletResponse response) {
        this(null, response);
    }

    public ResultTemple(HttpServletRequest request, HttpServletResponse response) {
        resultJson = ResultJsonFactory.create();
        this.request = request;
        this.response = response;
    }

    protected abstract void dosomething();

    public JSONObject result() {
        dosomething();
        resultJson.set(ServiceCode.SUCCESS);
        return resultJson.result();
    }

    public JSONObject resultError() {
        dosomething();
        return resultJson.result();
    }

    protected boolean verification() {
        return true;
    }
}
