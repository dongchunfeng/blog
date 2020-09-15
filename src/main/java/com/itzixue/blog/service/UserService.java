package com.itzixue.blog.service;

import com.itzixue.blog.entity.User;

public interface UserService {

    User checkUser(String username, String password);

}
