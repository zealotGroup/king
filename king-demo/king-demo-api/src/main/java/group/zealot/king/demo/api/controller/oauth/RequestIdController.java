package group.zealot.king.demo.api.controller.oauth;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.demo.api.config.RequestIdUtil;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Deprecated
@RestController
@RequestMapping("/requestId")
public class RequestIdController {


    @RequestMapping("/get")
    public JSONObject createAndGet() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                String requestId = null;
                while (requestId == null) {
                    requestId = RequestIdUtil.getRequestId();
                }
                JSONObject data = new JSONObject();
                data.put("requestId", requestId);
                data.put("timeout", RequestIdUtil.timeout.getSeconds());
                data.put("unit", TimeUnit.SECONDS);
                data.put("info", "不可续约");
                resultJson.set(data);
                resultJson.set(ServiceCode.SUCCESS);
            }
        }.result();
    }
}
