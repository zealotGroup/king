package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.redis.RedisUtil;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/requestId")
@Deprecated //取消使用requestId，防重复提交使用前端限制
public class RequestIdController {

    @Autowired
    private RedisUtil redisUtil;

    public static final String REQUEST_ID_NAME = "requestId";
    public static final String REDIS_PREFIX_REQUEST_ID = "api:requestId:";
    public static final Duration REQUEST_ID_TIMEOUT = Duration.ofMinutes(5L);

    @RequestMapping("createAndGet")
    public JSONObject createAndGet() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                String requestId;
                int size = 5;
                do {
                    requestId = Funcation.createTime() + "" + Funcation.createRandom(6);
                    Boolean fg = redisUtil.valueOperations().setIfAbsent(REDIS_PREFIX_REQUEST_ID + requestId, true,
                            REQUEST_ID_TIMEOUT);
                    if (fg) {
                        JSONObject data = new JSONObject();
                        data.put(REQUEST_ID_NAME, requestId);
                        data.put("timeout", REQUEST_ID_TIMEOUT.getSeconds());
                        data.put("unit", TimeUnit.SECONDS);
                        data.put("info", "不可续约");
                        resultJson.set(data);
                        resultJson.set(ServiceCode.SUCCESS);
                        break;
                    } else {
                        size--;
                    }
                } while (size > 0);
                if (size <= 0) {
                    throw new BaseRuntimeException("服务出现异常，请稍候再试");
                }
            }
        }.result();
    }
}
