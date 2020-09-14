package com.dhlk.light.factory.service;

import com.dhlk.entity.basicmodule.User;

public interface UserService {
    /**
     * 根据用户姓名查询用户
     * @param loginName
     * @return
     */
    User findUserByLoginName(String loginName);
}
