package com.project.bookstore.service;

import com.project.bookstore.model.User;

import java.util.List;

public interface UserService {

    public List<User> findAllUsers();

    public User findUserById(int theId);

    public void saveUser(User theUser);

    public void deleteUserById(int theId);

}
