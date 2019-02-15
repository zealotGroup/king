package group.zealot.king.core.zt.jms;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

public abstract class JmsConsumer {
    private final String destination = "";
    private final String containerFactory = "";

    public JmsConsumer() {
        super();
//        destination = "";
    }

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @JmsListener(destination = destination, containerFactory = containerFactory)
    public void updateOutboundNoticeFromTMS(String message) {
        if (logger.isInfoEnabled()) {
            logger.info("ActiveMqConsumer.updateOutboundNoticeFromTMS ---->start");
        }
    }


    ///**
    // * topic 订阅者消费
    // */
    //@JmsListener(destination = "", containerFactory = "jmsListenerContainerTopic")
    //public void messageFromTopic() {
    //
    //}

}
