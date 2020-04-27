package group.zealot.king.test;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.mq.rocketmq.RocketMQService;
import group.zealot.king.core.zt.mq.rocketmq.RocketmqUtil;
import group.zealot.king.demo.api.Run;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
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
    RocketMQService rocketMqService;

    @Autowired
    DefaultMQPushConsumer defaultMQPushConsumer;

    @Autowired
    RocketmqUtil rocketmqUtil;

    @Test
    public void sendJMS() {
        List<JSONObject> list = new ArrayList<>(1000);
        for (int i = 1000; i < 2000; i++) {
            JSONObject data = new JSONObject();
            data.put("number", i);
            list.add(data);
            rocketMqService.send("Test-1", data);
        }
        rocketMqService.send("Test-1", list);
    }

    @Test
    public void viewJMS() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        MessageExt messageExt = rocketmqUtil.viewMessage("Test-1", "1123");
        logger.info("queueId:{} queueOffset:{} msgId:{} body:{}", messageExt.getQueueId(), messageExt.getQueueOffset(), messageExt.getMsgId(), JSONObject.parseObject(messageExt.getBody(), JSONObject.class));
    }

    @Test
    public void revoverJMS() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        List<MessageExt> list = rocketmqUtil.getMessageExtList("Test-1", 1L);
        for (MessageExt messageExt : list) {
            logger.info("queueId:{} queueOffset:{} body:{}", messageExt.getQueueId(), messageExt.getQueueOffset(), JSONObject.parseObject(messageExt.getBody(), JSONObject.class));
        }
        logger.info("list size:{}", list.size());
    }


    @Test
    public void listenerJMS() throws MQClientException, InterruptedException {
        defaultMQPushConsumer.subscribe("Test-1", "*");
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(32);
        defaultMQPushConsumer.setConsumeThreadMin(4);
        defaultMQPushConsumer.setConsumeThreadMax(8);
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                for (MessageExt messageExt : list) {
                    logger.info("queueId:{} queueOffset:{} body:{}", messageExt.getQueueId(), messageExt.getQueueOffset(), JSONObject.parseObject(messageExt.getBody(), JSONObject.class));
                }
                logger.info("list size:{}", list.size());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                throw new BaseRuntimeException();
            }
        });
        defaultMQPushConsumer.start();
        defaultMQPushConsumer.shutdown();
        Thread.sleep(1000);
    }
}
