package group.zealot.king.core.zt.mq;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author zealot
 * @date 2020/4/17 22:16
 */
public interface MQService {
    void send(String topic, JSONObject jsonObject);

    void send(String topic, List<JSONObject> list);
}
