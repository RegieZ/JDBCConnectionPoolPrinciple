package com.regino.service;

import com.regino.domain.User;

public interface UserService {

    // 我来定义规范（根据用户名和密码查询User对象）
    public User login(String username, String password);
}