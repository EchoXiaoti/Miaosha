package com.first.miaosha.controller;

import com.first.miaosha.redis.RedisService;
import com.first.miaosha.result.Result;
import com.first.miaosha.service.MiaoshaUserService;
import com.first.miaosha.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @program: miaosha
 * @description: 登陆Controller
 * @author: Xiaoti
 * @create: 2018-06-21 15:40
 **/
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVO loginVO){
        log.info(loginVO.toString());
        //登陆
        String token = userService.login(response, loginVO);

        return Result.success(token);
    }
}
