package com.itzixue.blog.service.impl;

import com.itzixue.blog.dao.UserRepository;
import com.itzixue.blog.entity.User;
import com.itzixue.blog.service.UserService;
import com.itzixue.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Dong
 * @date 2020/5/21 13:03
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findUserByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
