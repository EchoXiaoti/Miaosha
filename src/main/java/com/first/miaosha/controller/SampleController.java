package com.first.miaosha.controller;

import com.first.miaosha.domain.User;
import com.first.miaosha.rabbitmq.MQSender;
import com.first.miaosha.redis.RedisService;
import com.first.miaosha.redis.UserKey;
import com.first.miaosha.result.Result;
import com.first.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: miaosha
 * @description: 举例
 * @author: Xiaoti
 * @create: 2018-06-20 11:15
 **/
@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name", "Echo");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user = redisService.get(UserKey.getById,"" + 1, User.class);

        return Result.success(user);
    }

    @RequestMapping("redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("1111");
        redisService.set(UserKey.getById,"" + 1, user);
        return Result.success(true);
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> topic() {
		sender.sendTopic("hello,imooc");
        return Result.success("Hello，world");
    }

}
