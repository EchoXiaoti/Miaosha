package com.first.miaosha.redis;

/**
 * @program: miaosha
 * @description: key前缀
 * @author: Xiaoti
 * @create: 2018-06-21 09:08
 **/
public interface KeyPrefix {

    public int expireSeconds(); //有效期

    public String getPrefix();  //前缀
}
