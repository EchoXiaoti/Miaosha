package com.first.miaosha.redis;

/**
 * @program: miaosha
 * @description:
 * @author: Xiaoti
 * @create: 2018-07-02 15:44
 **/
public class MiaoshaKey extends BasePrefix{

    private MiaoshaKey(String prefix){
        super(prefix);
    }

    public static MiaoshaKey getGoodsOver = new MiaoshaKey("go");
}
