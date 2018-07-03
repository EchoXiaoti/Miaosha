package com.first.miaosha.vo;

/**
 * @program: miaosha
 * @description: 登陆VO类
 * @author: Xiaoti
 * @create: 2018-06-22 10:00
 **/
public class LoginVO {

    private String mobile;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVO{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
