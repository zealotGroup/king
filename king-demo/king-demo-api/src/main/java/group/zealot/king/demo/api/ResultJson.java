package group.zealot.king.demo.api;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class ResultJson {
    private static final Logger logger = LoggerFactory.getLogger(ResultJson.class);
    private JSONObject jsonObject;

    ResultJson() {
        jsonObject = new JSONObject();
    }

    public ResultJson set(ServiceCode serviceCode) {
        return setCode(serviceCode.code()).setMsg(serviceCode.msg());
    }

    public ResultJson set(int code) {
        return setCode(code);
    }

    public ResultJson set(String msg) {
        return setMsg(msg);
    }

    public ResultJson setCode(int code) {
        jsonObject.put("code", code);
        return this;
    }

    public ResultJson setMsg(String msg) {
        jsonObject.put("msg", msg);
        return this;
    }

    public ResultJson put(String key, Object value) {
        if ("code".equals(key) || "msg".equals(key)) {
            logger.warn("key : " + key + " 不允许put");
        } else {
            jsonObject.put(key, value);
        }
        return this;
    }

    public String toJSONString() {
        if (!jsonObject.containsKey("code")) {
            logger.error("异常：返回结果不存在code");
            throw new BaseRuntimeException("异常：返回结果不存在code");
        }
        jsonObject.putIfAbsent("time", Instant.now());
        return jsonObject.toJSONString();
    }

    public ResultJson clone() {
        ResultJson clone = null;
        try {
            clone = (ResultJson) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
