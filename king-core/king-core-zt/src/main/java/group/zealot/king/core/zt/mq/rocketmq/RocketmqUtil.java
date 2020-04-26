package group.zealot.king.core.zt.mq.rocketmq;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.redis.RedisUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullStatus;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class RocketmqUtil {
    private static final String DEFAULT_TAG = "default_tag";
    private static final String KEY_PREFIX = "rocketmq:";
    private static final long MIN_OFFSET_STRING = Long.MIN_VALUE;
    private static final String SUB_EXPRESSION = "*";
    private static final long DEFAULT_TIME_OUT = 3000L;
    private static final int DEFAULT_MAX_NUMS = 64;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private DefaultMQProducer producer;
    @Autowired
    private DefaultMQPullConsumer consumer;

    public void send(String topic, JSONObject jsonObject) {
        send(toMessage(topic, DEFAULT_TAG, jsonObject));
    }

    public void send(String topic, List<JSONObject> list) {
        List<Message> messageList = new ArrayList<>(list.size());
        for (JSONObject jsonObject : list) {
            Message message = toMessage(topic, DEFAULT_TAG, jsonObject);
            messageList.add(message);
        }
        try {
            producer.send(messageList);
        } catch (Exception e) {
            throw new BaseRuntimeException(e);
        }
    }

    public void send(String topic, String tag, JSONObject jsonObject) {
        send(toMessage(topic, tag, jsonObject));
    }

    private Message toMessage(String topic, String tag, JSONObject jsonObject) {
        Message message = new Message();
        message.setTopic(topic);
        message.setTags(tag);
        message.setBody(jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8));
        logger.debug(message.toString());
        return message;
    }

    private SendStatus send(Message message) {
        return getSendStatus(message);
    }

    private SendStatus getSendStatus(Message message) {
        SendResult sendResult = getSendResult(message);
        return sendResult.getSendStatus();
    }

    private SendResult getSendResult(Message message) {
        try {
            return producer.send(message, DEFAULT_TIME_OUT);
        } catch (Exception e) {
            throw new BaseRuntimeException(e);
        }
    }

    /*============== pull 模式  ==============*/

    public List<MessageExt> getMessageExtList(String topic, long offset) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return getMessageExtList(topic, SUB_EXPRESSION, offset);
    }


    public List<MessageExt> getMessageExtList(String topic, String subExpression, long offset) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        List<MessageExt> messageExtList = new ArrayList<>();
        Set<MessageQueue> queueSet = getQueueSet(topic);
        for (MessageQueue queue : queueSet) {
            PullResult pullResult = consumer.pull(queue, subExpression, offset, DEFAULT_MAX_NUMS, DEFAULT_TIME_OUT);
            if (pullResult.getPullStatus() == PullStatus.FOUND) {
                messageExtList.addAll(pullResult.getMsgFoundList());
            }
        }
        return messageExtList;
    }

    private Set<MessageQueue> getQueueSet(String topic) throws MQClientException {
        return consumer.fetchSubscribeMessageQueues(topic);
    }

    /*============== pull 模式，对offset进行的管控 ==============*/

    public void consumed(MessageExt messageExt) {
        long offset = messageExt.getQueueOffset();
        int queueId = messageExt.getQueueId();
        String key = KEY_PREFIX + "queueId:" + queueId;
        long minOffset = getMinOffset(queueId);
        if (offset > minOffset) {
            putHashValue(key, MIN_OFFSET_STRING, offset);
        }
    }

    public boolean isNewMessageExt(MessageExt messageExt) {
        long offset = messageExt.getQueueOffset();
        long queueId = messageExt.getQueueId();
        String key = KEY_PREFIX + "queueId:" + queueId;
        return !hasHashKey(key, offset);
    }

    private void setMinOffset(int queueId, long offset) {
        String key = KEY_PREFIX + "queueId:" + queueId;
        putHashValue(key, MIN_OFFSET_STRING, offset);
    }

    public Long getMinOffset(int queueId) {
        String key = KEY_PREFIX + "queueId:" + queueId;
        return getHashValue(key, MIN_OFFSET_STRING);
    }

    private <H, HK, HV> boolean hasHashKey(H h, HK hk) {
        HashOperations<H, HK, HV> hashOperations = redisUtil.hashOperations();
        return hashOperations.hasKey(h, hk);
    }

    private <H, HK, HV> HV getHashValue(H h, HK hk) {
        HashOperations<H, HK, HV> hashOperations = redisUtil.hashOperations();
        return hashOperations.get(h, hk);
    }

    private <H, HK, HV> void putHashValue(H h, HK hk, HV hv) {
        HashOperations<H, HK, HV> hashOperations = redisUtil.hashOperations();
        hashOperations.put(h, hk, hv);
    }
}
