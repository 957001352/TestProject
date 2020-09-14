package com.dhlk.light.factory.service.impl;

import com.dhlk.entity.basicmodule.User;
import com.dhlk.light.factory.dao.UserDao;
import com.dhlk.light.factory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByLoginName(String loginName) {
        return userDao.findUserByLoginName(loginName);
    }
}
