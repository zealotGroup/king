package group.zealot.king.core.zt.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;


@Component
public class RedisUtil {
    @Autowired
    private LettuceConnectionFactory connectionFactory;

    private final TimeUnit timeUnit = TimeUnit.SECONDS;

    private RedisConnection redisConnection() {
        return connectionFactory.getConnection();
    }

    public <K, V> RedisTemplate<K, V> redisTemplate() {
        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    public <K, V> ZSetOperations<K, V> zSetOperations() {
        RedisTemplate<K, V> redisTemplate = redisTemplate();
        return redisTemplate.opsForZSet();
    }

    public <K, HK, HV> HashOperations<K, HK, HV> hashOperations() {
        RedisTemplate<K, HV> redisTemplate = redisTemplate();
        return redisTemplate.opsForHash();
    }

    public StringRedisTemplate stringRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = redisTemplate();
        return (StringRedisTemplate) redisTemplate;
    }

    public void set(String key, String value) {
        stringRedisTemplate().opsForValue().set(key, value);
    }

    public void set(String key, String value, long timeout) {
        stringRedisTemplate().opsForValue().set(key, value, timeout, timeUnit);
    }

    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate().opsForValue().set(key, value, timeout, timeUnit);
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
        return setExpire(key, timeout, timeUnit);
    }

    public boolean setExpire(String key, long timeout, TimeUnit timeUnit) {
        return stringRedisTemplate().expire(key, timeout, timeUnit);
    }

    /**
     * 清空所有key【慎用】
     */
    public void flushAll() {
        RedisConnection redisConnection = redisConnection();
        redisConnection.flushAll();
    }

}
