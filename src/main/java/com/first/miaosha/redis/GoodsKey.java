package com.first.miaosha.redis;

/**
 * @program: miaosha
 * @description: 商品redisKey
 * @author: Xiaoti
 * @create: 2018-06-27 16:47
 **/
public class GoodsKey extends BasePrefix{

    private GoodsKey(int expireSeconds, String prefix){
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"id");

    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");

    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0,"gs");
}
