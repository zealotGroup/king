package group.zealot.king.core.zt.mq.rocketmq;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NacosPropertySource(dataId = "rocketmq", autoRefreshed = true, type = ConfigType.YAML)
public class RocketmqConfig {

    @Value("${rocketmq.namesrv-addr}")
    public String namesrvAddr;
    @Value("${rocketmq.group}")
    public String group;

    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(namesrvAddr);
        producer.setProducerGroup(group);
        producer.setVipChannelEnabled(false);
        producer.start();
        return producer;
    }
}
