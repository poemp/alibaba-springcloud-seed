package org.poem.databases.redis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author poem
 */
@Service
 
@ConditionalOnExpression("environment['spring.redis.host'] != ''  && environment['spring.redis.host'] != null  ")
@ConfigurationProperties(prefix = "spring.warehouse.app")
@ConditionalOnClass({JedisConnection.class})
public class ContextRedisUtils {


    private static final Logger logger = LoggerFactory.getLogger(ContextRedisUtils.class);


    private RedisTemplate redisTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;


    @PostConstruct
    public void init() {
        if (StringUtils.isBlank(host)) {
            return;
        }
        logger.info("redis: " +
                "\n\t\t [host]:" + host +
                "\n\t\t [port]:" + port +
                "\n\t\t [password]:" + password
        );
        ListOperations list = redisTemplate.opsForList();
        try {
            list.size("");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            SpringApplication.exit(applicationContext, new ExitCodeGenerator() {
                @Override
                public int getExitCode() {
                    System.exit(1000000000);
                    return 1000000000;
                }
            });
        }
    }

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer<Object> stringSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;


    }

    /**
     * 情况redis 缓存
     */
    public void flushDB() {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        if (value == null) {
            return true;
        }
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            logger.info("redis set key exception");
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            logger.info("redis set key exception");
        }
        return result;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 哈希获取数据all
     *
     * @param key
     * @return
     */
    public List<Object> hmvals(String key) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.values(key);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表只保留指定区间内的元素
     *
     * @param k
     * @param start
     * @param end
     */
    public void lTrim(String k, long start, long end) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.trim(k, start, end);
    }

    /**
     * 列表添加置顶索引
     *
     * @param k
     * @param v
     */
    public void lset(String k, long k2, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.set(k, k2, v);
    }

    /**
     * 获取列表长度
     *
     * @param k
     */
    public long lsize(String k) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.size(k);
    }

    /**
     * 列表弹出最右边的元素
     *
     * @param k
     */
    public Object rightPop(String k) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.rightPop(k);
    }

    /**
     * 列表弹出最右边的元素
     *
     * @param k
     */
    public Object leftPop(String k) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.leftPop(k);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset;
        zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }
}
