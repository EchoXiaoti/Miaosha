package com.first.miaosha.service;

import com.first.miaosha.domain.MiaoshaOrder;
import com.first.miaosha.domain.MiaoshaUser;
import com.first.miaosha.domain.OrderInfo;
import com.first.miaosha.redis.MiaoshaKey;
import com.first.miaosha.redis.RedisService;
import com.first.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: miaosha
 * @description:
 * @author: Xiaoti
 * @create: 2018-06-26 13:15
 **/
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减少库存下订单写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if(success){
            return orderService.createOrder(user, goods);
        }else{
            setGoodsOver(goods.getId());
        }
        return null;
    }

    public long getMiaoshaResult(Long id, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(id, goodsId);
        if(order != null){
            return order.getOrderId();
        }else{
            boolean isOver = getGoodsOver(goodsId);
            if(isOver){
                return -1;
            }else{
                return 0;
            }
        }
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.getGoodsOver, "" + goodsId);
    }

    private void setGoodsOver(Long id) {
        redisService.set(MiaoshaKey.getGoodsOver, "" + id, true);
    }
}
