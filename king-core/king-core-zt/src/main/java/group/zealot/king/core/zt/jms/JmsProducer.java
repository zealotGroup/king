package group.zealot.king.core.zt.jms;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Queue;
import javax.jms.Topic;

public abstract class JmsProducer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    protected abstract Queue getQueue();

    protected abstract Topic getTopic();

    public void sendQueue(JSONObject jsonObject) {
        jmsMessagingTemplate.convertAndSend(getQueue(), jsonObject);
    }

    public void sendTopic(JSONObject jsonObject) {
        jmsMessagingTemplate.convertAndSend(getTopic(), jsonObject);
    }
}
