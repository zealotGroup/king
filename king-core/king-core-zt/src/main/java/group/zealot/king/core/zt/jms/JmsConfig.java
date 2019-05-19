package group.zealot.king.core.zt.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.RedeliveryPolicy;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.Session;
import javax.jms.TextMessage;

@Configuration
public class JmsConfig {
    @Autowired
    Environment environment;

    @Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(JmsPoolConnectionFactory jmsPoolConnectionFactory) {
        ActiveMQConnectionFactory factory = (ActiveMQConnectionFactory) jmsPoolConnectionFactory.getConnectionFactory();
        RedeliveryPolicy redeliveryPolicy = factory.getRedeliveryPolicy();
        redeliveryPolicy.setUseExponentialBackOff(environment.getProperty("spring.activemq.redeliveryPolicy.useExponentialBackOff",Boolean.class));//重发时间间隔递增
        redeliveryPolicy.setBackOffMultiplier(environment.getProperty("spring.activemq.redeliveryPolicy.backOffMultiplier",Double.class));//重发时间间隔倍数增长，需 UseExponentialBackOff 为true
        redeliveryPolicy.setMaximumRedeliveries(environment.getProperty("spring.activemq.redeliveryPolicy.maximumRedeliveries",Integer.class));//最大重发次数
        redeliveryPolicy.setMaximumRedeliveryDelay(environment.getProperty("spring.activemq.redeliveryPolicy.maximumRedeliveryDelay",Long.class));//最大重发间隔时间
        redeliveryPolicy.setInitialRedeliveryDelay(environment.getProperty("spring.activemq.redeliveryPolicy.initialRedeliveryDelay",Long.class));//初始重发时间间隔

        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(jmsPoolConnectionFactory);
        defaultJmsListenerContainerFactory.setSessionAcknowledgeMode(ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);//客户端调用acknowledge方法手动签收  activemq专用4
        defaultJmsListenerContainerFactory.setPubSubDomain(false);
        return defaultJmsListenerContainerFactory;
    }

    @JmsListener(destination = "destination", containerFactory = "defaultJmsListenerContainerFactory", concurrency = "1-2")
    public void recover(TextMessage message, Session session) {

    }
}
