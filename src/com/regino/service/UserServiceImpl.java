package com.regino.service;

import com.regino.dao.UserDao;
import com.regino.dao.UserDaoImpl;
import com.regino.domain.User;

public class UserServiceImpl implements UserService {

    @Override
    public User login(String username, String password) {
        UserDao userDao = new UserDaoImpl();
        return userDao.login(username, password);
    }
}