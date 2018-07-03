package com.first.miaosha.rabbitmq;

import com.first.miaosha.domain.MiaoshaUser;

/**
 * @program: miaosha
 * @description: 消息类
 * @author: Xiaoti
 * @create: 2018-06-29 14:02
 **/
public class MiaoshaMessage {

    private MiaoshaUser user;

    private long goodsId;

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
