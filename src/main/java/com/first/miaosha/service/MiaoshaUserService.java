package com.first.miaosha.service;

import com.first.miaosha.dao.MiaoshaUserDao;
import com.first.miaosha.domain.MiaoshaUser;
import com.first.miaosha.exception.GlobalException;
import com.first.miaosha.redis.MiaoshaUserKey;
import com.first.miaosha.redis.RedisService;
import com.first.miaosha.result.CodeMsg;
import com.first.miaosha.utils.MD5Util;
import com.first.miaosha.utils.UUIDUtil;
import com.first.miaosha.vo.LoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: miaosha
 * @description: 秒杀系统UserService
 * @author: Xiaoti
 * @create: 2018-06-22 10:05
 **/

@Service
public class MiaoshaUserService {

    private static Logger log = LoggerFactory.getLogger(MiaoshaUser.class);

    @Autowired
    MiaoshaUserDao userDao;

    @Autowired
    RedisService redisService;

    public static final String COOKI_NAME_TOKEN = "token";

    public String login(HttpServletResponse response, LoginVO loginVO){
        if(loginVO == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVO.getMobile();
        String formpass = loginVO.getPassword();

        //判断是否存在
        MiaoshaUser user = userDao.getById(Long.parseLong(mobile));
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_EMPTY);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formpass, saltDB);
        log.info("formPass is {}", formpass);
        log.info("saltDB is {}", saltDB);
        log.info("dbPass is {}", dbPass);
        log.info("calcPss is {}", calcPass);
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成Cookie
        String token = UUIDUtil.uuid().replace("-", "");
        addCookie(response, token, user);
        return token;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if(user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    public MiaoshaUser getById(long id){
        //取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, "" + id, MiaoshaUser.class);
        if(user != null){
            return user;
        }
        //取数据库
        user = userDao.getById(id);
        if(user != null){
            redisService.set(MiaoshaUserKey.token, "" + id, user);
        }
        return user;
    }

    public boolean updatePassword(String token, long id, String formPassword){
        //取user
        MiaoshaUser user = getById(id);
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPassword, user.getSalt()));
        userDao.update(toBeUpdate);
        //处理缓存
        redisService.delete(MiaoshaUserKey.getById, "" + id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token, token, user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user){
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
