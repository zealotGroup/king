package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.core.zt.redis.RedisUtil;
import group.zealot.king.demo.api.config.ResultFul;
import group.zealot.king.demo.api.config.ResultJson;
import group.zealot.king.demo.api.config.ResultJsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static group.zealot.king.demo.api.config.RequestFilter.REQUEST_ID_KEY_PREFIX;
import static group.zealot.king.demo.api.config.RequestFilter.REQUEST_ID_TIMEOUT;

@RestController
@RequestMapping("/requestId")
public class RequestIdController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("createAndGet")
    public JSONObject createAndGet() {
        return new ResultFul() {
            @Override
            protected void dosomething() {
                String requestId;
                while (true) {
                    requestId = Funcation.createRequestId();
                    Boolean fg = redisUtil.valueOperations().setIfAbsent(REQUEST_ID_KEY_PREFIX + requestId, requestId,
                            REQUEST_ID_TIMEOUT, TimeUnit.SECONDS);
                    if (fg) {
                        break;
                    }
                }
                resultJson.put("requestId", requestId);
                resultJson.set(ServiceCode.SUCCESS);
            }
        }.result();
    }
}
