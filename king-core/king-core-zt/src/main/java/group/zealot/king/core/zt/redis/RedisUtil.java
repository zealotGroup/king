package group.zealot.king.core.zt.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;


@Component
public class RedisUtil {
    @Autowired
    private LettuceConnectionFactory connectionFactory;

    private RedisConnection redisConnection() {
        return connectionFactory.getConnection();
    }

    private <K, V> RedisTemplate<K, V> redisTemplate() {
        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    private StringRedisTemplate stringRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = redisTemplate();
        return (StringRedisTemplate) redisTemplate;
    }

    public void set(String key, String value) {
        stringRedisTemplate().opsForValue().set(key, value);
    }

    public String get(String key) {
        return stringRedisTemplate().opsForValue().get(key);
    }

    public void delete(String key) {
        stringRedisTemplate().delete(key);
    }

    public Set<String> getKeys(String key) {
        return stringRedisTemplate().keys(key);
    }

    public boolean setExpire(String key, long timeout) {
        return setExpire(key, timeout, TimeUnit.SECONDS);
    }

    public boolean setExpire(String key, long timeout, TimeUnit timeUnit) {
        return stringRedisTemplate().expire(key, timeout, timeUnit);
    }

    public int keys(String key){
        return stringRedisTemplate().keys(key);
    }

    /**
     * 清空所有key【慎用】
     */
    public void flushAll() {
        RedisConnection redisConnection = redisConnection();
        redisConnection.flushAll();
    }

}
