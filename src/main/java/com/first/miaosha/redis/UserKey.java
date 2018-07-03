package com.first.miaosha.redis;

/**
 * @program: miaosha
 * @description: 用户key
 * @author: Xiaoti
 * @create: 2018-06-21 09:12
 **/
public class UserKey extends BasePrefix {

    private UserKey(String prefix){
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");

    public static UserKey getByName = new UserKey("name");
}
