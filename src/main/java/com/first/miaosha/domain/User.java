package com.first.miaosha.domain;

/**
 * @program: miaosha
 * @description: 用户对象
 * @author: Xiaoti
 * @create: 2018-06-20 13:41
 **/
public class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
