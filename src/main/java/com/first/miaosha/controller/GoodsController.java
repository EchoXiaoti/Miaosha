package com.first.miaosha.controller;

import com.first.miaosha.domain.MiaoshaUser;
import com.first.miaosha.redis.GoodsKey;
import com.first.miaosha.redis.RedisService;
import com.first.miaosha.service.GoodsService;
import com.first.miaosha.service.MiaoshaUserService;
import com.first.miaosha.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @program: miaosha
 * @description: 商品Controller
 * @author: Xiaoti
 * @create: 2018-06-25 10:02
 **/

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user){
            model.addAttribute("user", user);
            //取缓存
            String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
            if(!StringUtils.isEmpty(html)) {
                return html;
            }
            List<GoodsVo> goodsList = goodsService.listGoodsVo();
            model.addAttribute("goodsList", goodsList);
//    	 return "goods_list";
            WebContext ctx = new WebContext(request,response,
                    request.getServletContext(),request.getLocale(), model.asMap());
            //手动渲染
            html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
            if(!StringUtils.isEmpty(html)) {
                redisService.set(GoodsKey.getGoodsList, "", html);
            }
            return html;
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser user, @PathVariable("goodsId")long goodsId){

        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        model.addAttribute("goods", goods);

        long starAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;

        if(now < starAt){//还未开始
            miaoshaStatus = 0;
        }else if(now > endAt){//已经结束
            remainSeconds = (int)(starAt - now)/1000;
            miaoshaStatus = 2;
        }else {//进行中
            miaoshaStatus = 1;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        return "goods_detail";
    }
}
