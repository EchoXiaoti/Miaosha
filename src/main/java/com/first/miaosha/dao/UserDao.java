package com.first.miaosha.dao;

import com.first.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @program: miaosha
 * @description:
 * @author: Xiaoti
 * @create: 2018-06-20 13:42
 **/

@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id")int id);

    @Insert("insert into user(name)values(#{name})")
    public int insert(User user);
}
