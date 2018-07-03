package com.first.miaosha.utils;

import java.util.UUID;

/**
 * @program: miaosha
 * @description: uuid工具类
 * @author: Xiaoti
 * @create: 2018-06-22 11:05
 **/
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
