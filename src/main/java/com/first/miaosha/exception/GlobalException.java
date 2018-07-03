package com.first.miaosha.exception;

import com.first.miaosha.result.CodeMsg;

/**
 * @program: miaosha
 * @description: 全局异常类
 * @author: Xiaoti
 * @create: 2018-06-22 10:22
 **/
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

    public void setCm(CodeMsg cm) {
        this.cm = cm;
    }
}
