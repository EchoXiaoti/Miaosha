package com.first.miaosha.redis;

/**
 * @program: miaosha
 * @description: key前缀实现类
 * @author: Xiaoti
 * @create: 2018-06-21 09:10
 **/
public abstract class BasePrefix implements KeyPrefix{

    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix){   //0表示永不过期
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {    //默认0代表永不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
