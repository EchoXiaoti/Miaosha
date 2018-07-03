package com.first.miaosha.redis;

/**
 * @program: miaosha
 * @description: 秒杀用户redis前缀
 * @author: Xiaoti
 * @create: 2018-06-22 17:01
 **/
public class MiaoshaUserKey extends BasePrefix{

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");

    public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");
}
