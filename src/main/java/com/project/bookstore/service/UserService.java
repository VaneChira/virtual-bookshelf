package com.project.bookstore.service;

import com.project.bookstore.model.User;

import java.util.List;

public interface UserService {

     List<User> findAllUsers();

     User findUserById(Long id);

     User saveUser(User user);

     void deleteUserById(Long id);

    User updateUser(User user, Long id);
}
