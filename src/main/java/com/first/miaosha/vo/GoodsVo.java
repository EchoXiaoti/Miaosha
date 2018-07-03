package com.first.miaosha.vo;

import com.first.miaosha.domain.Goods;

import java.util.Date;

/**
 * @program: miaosha
 * @description: 自定义商品类
 * @author: Xiaoti
 * @create: 2018-06-26 09:05
 **/
public class GoodsVo extends Goods {

    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private Double miaoshaPrice;

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(Double miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }
}
