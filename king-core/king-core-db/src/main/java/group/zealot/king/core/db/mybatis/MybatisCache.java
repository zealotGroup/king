package group.zealot.king.core.db.mybatis;

import group.zealot.king.base.exception.BaseRuntimeException;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MybatisCache implements Cache {
    private static long timeout = 1 * 60L;//单位秒

    private static TimeUnit TIME_UNIT = TimeUnit.SECONDS;//单位秒

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String id;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public MybatisCache(final String id) {
        if (id == null) {
            throw new BaseRuntimeException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Object getObject(Object key) {
        Object result = null;
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = getRedisSerializer(key);
            result = serializer.deserialize(connection.get(serializer.serialize(key)));
        } catch (JedisConnectionException e) {
            logger.error("redis getObject() Exception", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    @Override
    public int getSize() {
        int result = 0;
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            result = Integer.valueOf(connection.dbSize().toString());
        } catch (JedisConnectionException e) {
            logger.error("redis getSize() Exception", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    @Override
    public void putObject(Object key, Object value) {
        RedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = getRedisSerializer(key);
            connection.set(serializer.serialize(key), serializer.serialize(value), Expiration.from(timeout, TIME_UNIT),
                    RedisStringCommands.SetOption.UPSERT);//NX key不存在则插入【insert】  XX key存在，则覆盖【update】  UPSERT不作判断
        } catch (JedisConnectionException e) {
            logger.error("redis putObject() Exception", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    public Object removeObject(Object key) {
        RedisConnection connection = null;
        Object result = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer = getRedisSerializer(key);
            result = connection.expire(serializer.serialize(key), 0);
        } catch (JedisConnectionException e) {
            logger.error("redis removeObject() Exception", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }


    private RedisSerializer<Object> getRedisSerializer(Object key) {
        if (isJdkSerializat) {
            return new Jackson2JsonRedisSerializer(key.getClass());
        }
        return new JdkSerializationRedisSerializer();
    }
}
