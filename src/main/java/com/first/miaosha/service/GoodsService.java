package com.first.miaosha.service;

import com.first.miaosha.dao.GoodsDao;
import com.first.miaosha.domain.MiaoshaGoods;
import com.first.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: miaosha
 * @description: 商品类Service
 * @author: Xiaoti
 * @create: 2018-06-26 09:04
 **/
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
       return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoById(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods) {

        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(g);
        return ret > 0;
    }
}
