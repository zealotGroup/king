package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ResultTemple {
    @Getter
    protected final ResultJson resultJson;

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ResultTemple() {
        this(null, null);
    }

    public ResultTemple(HttpServletRequest request) {
        this(request, null);
    }

    public ResultTemple(HttpServletRequest request, HttpServletResponse response) {
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
