package com.first.miaosha.dao;

import com.first.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @program: miaosha
 * @description: 秒杀用户DAO层
 * @author: Xiaoti
 * @create: 2018-06-22 10:10
 **/
@Mapper
public interface MiaoshaUserDao {

    @Select("select * from miaosha_user where id = #{id}")
    public MiaoshaUser getById(@Param("id")long id);

    @Update("update miaosh_user set password = #{password} where id = #{id}")
    public void update(MiaoshaUser toBeUpdate);
}
