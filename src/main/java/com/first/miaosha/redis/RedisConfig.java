package com.first.miaosha.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

/**
 * @program: miaosha
 * @description: redis配置
 * @author: Xiaoti
 * @create: 2018-06-20 15:55
 **/

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    Logger logger = LoggerFactory.getLogger(RedisCacheConfiguration.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxActive() {
        return maxActive;
    }
}
