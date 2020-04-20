package group.zealot.king.core.zt.mq;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zealot
 * @date 2020/4/17 22:16
 */
public interface MQService {
    void send(String topic, JSONObject jsonObject);
}
