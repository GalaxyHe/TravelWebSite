package com.travel.service;

import com.travel.bean.User;

public interface UserService {
    /**
     * 注册
     * @param user
     * @return
     */
    boolean setUser(User user);

    /**
     * 用户激活
     * @param code
     * @return
     */
    boolean active(String code);


    /**
     * 根据用户名和密码查询用户
     * @param user
     * @return
     */
    User findByUsernameAndPassword(User user);


}
