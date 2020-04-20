package group.zealot.king.core.zt.mq;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.mq.rocketmq.RocketmqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zealot
 * @date 2020/4/17 22:16
 */
@Component
public class MQUtil implements MQService {
    @Autowired
    RocketmqUtil rocketmqUtil;

    @Override
    public void send(String topic, JSONObject jsonObject) {
        if (!rocketmqUtil.send(topic, jsonObject)) {
            throw new BaseRuntimeException("发送消息异常");
        }
    }
}
