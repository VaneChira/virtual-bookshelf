package com.project.bookstore.service;

import com.project.bookstore.model.User;

import java.util.List;

public interface UserService {

     List<User> findAllUsers();

     User findUserById(int theId);

     User saveUser(User theUser);

     void deleteUserById(int theId);

    User updateUser(User user, int id);
}
