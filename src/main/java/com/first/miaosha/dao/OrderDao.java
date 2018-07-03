package com.first.miaosha.dao;

import com.first.miaosha.domain.MiaoshaOrder;
import com.first.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @program: miaosha
 * @description:
 * @author: Xiaoti
 * @create: 2018-06-26 13:19
 **/
@Mapper
public interface OrderDao {

    @Select("select * from miaosha_order where user_id = #{userId} and goods_id = #{goodsId}")
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId")Long userId, @Param("goodsId")long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date) values" +
            "(#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    public long insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order(user_id, goods_id, order_id) values (#{userId}, #{goodsId}, #{orderId})")
    public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

}
