package com.first.miaosha.rabbitmq;

import com.first.miaosha.domain.MiaoshaOrder;
import com.first.miaosha.domain.MiaoshaUser;
import com.first.miaosha.redis.RedisService;
import com.first.miaosha.service.GoodsService;
import com.first.miaosha.service.MiaoshaService;
import com.first.miaosha.service.OrderService;
import com.first.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: miaosha
 * @description: 消息接收
 * @author: Xiaoti
 * @create: 2018-06-29 13:59
 **/
@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);
        MiaoshaMessage mm  = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();

        //判断库存
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        miaoshaService.miaosha(user, goods);
    }

//		@RabbitListener(queues=MQConfig.QUEUE)
//		public void receive(String message) {
//			log.info("receive message:"+message);
//		}
//
		@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
		public void receiveTopic1(String message) {
			log.info(" topic  queue1 message:"+message);
		}

		@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
		public void receiveTopic2(String message) {
			log.info(" topic  queue2 message:"+message);
		}
//
//		@RabbitListener(queues=MQConfig.HEADER_QUEUE)
//		public void receiveHeaderQueue(byte[] message) {
//			log.info(" header  queue message:"+new String(message));
//		}
//
}
