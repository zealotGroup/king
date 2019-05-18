package group.zealot.king.core.zt.jms;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsUtil {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(String destination, String message) {
        jmsMessagingTemplate.convertAndSend(destination, message);
    }

    public void send(String destination, Object message) {
        jmsMessagingTemplate.convertAndSend(destination, JSONObject.toJSONString(message));
    }
}
