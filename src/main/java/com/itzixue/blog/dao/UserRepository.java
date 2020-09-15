package com.itzixue.blog.dao;

import com.itzixue.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByUsernameAndPassword(String username,String password);
}
