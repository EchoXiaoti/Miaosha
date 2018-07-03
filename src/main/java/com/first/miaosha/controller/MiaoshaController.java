package com.first.miaosha.controller;

import com.first.miaosha.domain.MiaoshaOrder;
import com.first.miaosha.domain.MiaoshaUser;
import com.first.miaosha.rabbitmq.MQSender;
import com.first.miaosha.rabbitmq.MiaoshaMessage;
import com.first.miaosha.redis.GoodsKey;
import com.first.miaosha.redis.RedisService;
import com.first.miaosha.result.CodeMsg;
import com.first.miaosha.result.Result;
import com.first.miaosha.service.GoodsService;
import com.first.miaosha.service.MiaoshaService;
import com.first.miaosha.service.OrderService;
import com.first.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: miaosha
 * @description: 秒杀Controller
 * @author: Xiaoti
 * @create: 2018-06-26 11:12
 **/
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    GoodsService goodsService;

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;

    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(Model model, MiaoshaUser user, @RequestParam(value = "goodsId", defaultValue = "0")long goodsId){
        model.addAttribute("user", user);
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if(stock < 0){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null){
            return Result.error(CodeMsg.REPAET_MIAOSHA);
        }
        //入队
        MiaoshaMessage message = new MiaoshaMessage();
        message.setUser(user);
        message.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(message);
        return Result.success(0);
        /*
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //判断是否已经秒杀到
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null){
            model.addAttribute("errmsg", CodeMsg.REPAET_MIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //减库存下订单写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);

        return "order_detail";
        */
    }

    /**
     * orderId:成功
     * -1：秒杀失败
     * 0:排队中
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, MiaoshaUser user, @RequestParam("goodsId")long goodsId){
        model.addAttribute("user", user);
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }

    /**
     * 系统初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if(goodsVoList == null){
            return;
        }
        for(GoodsVo goods : goodsVoList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goods.getId(), goods.getStockCount());
        }
    }
}
