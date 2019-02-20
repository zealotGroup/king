package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.core.zt.redis.RedisUtil;
import group.zealot.king.demo.api.config.ResultFul;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/requestId")
public class RequestIdController {

    @Autowired
    private RedisUtil redisUtil;

    public static final String REQUEST_ID_NAME = "requestId";
    public static final String REDIS_PREFIX_REQUEST_ID = "api:requestId:";
    public static final Duration REQUEST_ID_TIMEOUT = Duration.ofMinutes(30L);

    @RequestMapping("createAndGet")
    public JSONObject createAndGet() {
        return new ResultFul() {
            @Override
            protected void dosomething() {
                String requestId;
                while (true) {
                    requestId = Funcation.createRequestId();
                    Boolean fg = redisUtil.valueOperations().setIfAbsent(REDIS_PREFIX_REQUEST_ID + requestId, requestId,
                            REQUEST_ID_TIMEOUT);
                    if (fg) {
                        break;
                    }
                }
                resultJson.put(REQUEST_ID_NAME, requestId);
                resultJson.set(ServiceCode.SUCCESS);
            }
        }.result();
    }
}
