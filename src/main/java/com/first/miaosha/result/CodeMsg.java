package com.first.miaosha.result;

/**
 * @program: miaosha
 * @description: 通用返回信息
 * @author: Xiaoti
 * @create: 2018-06-20 13:32
 **/
public class CodeMsg {

    private int code;
    private String msg;

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常:%s");

    //登陆模块5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登陆密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");

    //秒杀模块5005XX
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500, "商品已经秒杀完");
    public static CodeMsg REPAET_MIAOSHA = new CodeMsg(500501, "商品重复秒杀");

    private CodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... objects){
        int code = this.code;
        String message = String.format(this.msg, objects);
        return new CodeMsg(code, message);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
