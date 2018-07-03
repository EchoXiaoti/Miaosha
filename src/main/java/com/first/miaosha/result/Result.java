package com.first.miaosha.result;

/**
 * @program: miaosha
 * @description: 返回结果通用类
 * @author: Xiaoti
 * @create: 2018-06-20 13:26
 **/
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    private Result(T data){
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm){
        if(cm == null)
            return;
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    /**
     * 返回成功时调用
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 返回失败时调用
     * @param cm
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm){
        return new Result<T>(cm);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
