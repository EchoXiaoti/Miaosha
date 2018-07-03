package com.first.miaosha.utils;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @program: miaosha
 * @description: MD5工具类
 * @author: Xiaoti
 * @create: 2018-06-21 15:19
 **/
public class MD5Util {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassFormPass(String inputPass){
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt){
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String input, String saltDB){
        String formPass = inputPassFormPass(input);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args){
        String password = "123456";
        System.out.println(inputPassFormPass(password));
        System.out.println(formPassToDBPass(inputPassFormPass(password), salt));
        System.out.println(inputPassToDBPass(password, salt));
    }
}
