package group.zealot.king.test;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.mq.MQUtil;
import group.zealot.king.core.zt.mq.rocketmq.DefaultMQConsume;
import group.zealot.king.demo.api.Run;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * @author zealot
 * @date 2019/11/16 12:05
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = {Run.class})
public class MainTest {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ApplicationContext context;

    @Autowired
    MQUtil mqUtil;
    @Autowired
    DefaultMQConsume consume;

    @Value("${rocketmq.namesrv-addr}")
    public String namesrvAddr;
    @Value("${rocketmq.group}")
    public String group;

    @Test
    public void sendJMS() {
        JSONObject data = new JSONObject();
        data.put("number", 1);
        mqUtil.send("Test-1", data);
    }

    @Test
    public void revoverJMS() throws MQClientException, InterruptedException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();

        // Specify name server addresses.
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumerGroup(group);
        // Subscribe one more more topics to consume.
        consumer.subscribe("Test-1", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        consumer.start();

        Thread.sleep(1000);
    }
}
