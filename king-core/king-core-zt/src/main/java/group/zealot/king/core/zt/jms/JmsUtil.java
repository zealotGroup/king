package group.zealot.king.core.zt.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsUtil {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void ser(){
        jmsMessagingTemplate.getJmsTemplate().getSessionAcknowledgeMode();
    }
}
