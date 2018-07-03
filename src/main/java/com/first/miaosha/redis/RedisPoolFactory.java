package com.first.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: miaosha
 * @description: redis池
 * @author: Xiaoti
 * @create: 2018-06-20 16:40
 **/
@Service
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;

    @Bean
    public JedisPool jedisPoolFactory(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getMaxActive());
        poolConfig.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
        JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout() * 1000, redisConfig.getPassword(), 0);
        return jp;
    }
}
