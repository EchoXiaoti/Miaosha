package com.first.miaosha.service;

import com.first.miaosha.dao.UserDao;
import com.first.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: miaosha
 * @description: 用户service
 * @author: Xiaoti
 * @create: 2018-06-20 13:44
 **/

@Service("userService")
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx(){
        User user = new User();
        user.setId(2);
        user.setName("2222");
        userDao.insert(user);

        User user2 = new User();
        user2.setId(1);
        user2.setName("1111");
        userDao.insert(user2);

        return true;
    }
}
