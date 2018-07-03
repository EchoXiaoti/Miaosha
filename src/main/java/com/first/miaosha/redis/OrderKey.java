package com.first.miaosha.redis;

/**
 * @program: miaosha
 * @description: 订单key
 * @author: Xiaoti
 * @create: 2018-06-21 09:14
 **/
public class OrderKey extends BasePrefix {

    public OrderKey(int expireSeconds, String prefix){
        super(expireSeconds, prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey(0,"ok");
}
