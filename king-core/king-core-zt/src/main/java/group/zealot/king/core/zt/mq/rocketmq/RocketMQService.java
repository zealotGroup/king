package group.zealot.king.core.zt.mq.rocketmq;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.mq.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zealot
 * @date 2020/4/17 22:16
 */
@Component
public class RocketMQService implements MQService {
    @Autowired
    RocketmqUtil rocketmqUtil;

    @Override
    public void send(String topic, JSONObject jsonObject) {
        rocketmqUtil.send(topic, jsonObject);
    }

    @Override
    public void send(String topic, List<JSONObject> list) {
        rocketmqUtil.send(topic, list);
    }
}
