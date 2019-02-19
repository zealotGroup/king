package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ResultFul {
    @Getter
    protected final ResultJson resultJson;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ResultFul() {
        this(null, null);
    }

    public ResultFul(HttpServletRequest request) {
        this(request, null);
    }

    public ResultFul(HttpServletRequest request, HttpServletResponse response) {
        resultJson = ResultJsonFactory.create();
        this.request = request;
        this.response = response;
    }

    protected abstract void dosomething();

    public JSONObject result() {
        dosomething();
        return resultJson.result();
    }
}
