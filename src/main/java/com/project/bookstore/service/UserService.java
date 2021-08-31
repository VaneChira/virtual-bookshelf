package com.project.bookstore.service;

import com.project.bookstore.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> findAllUsers();

    User findUserById(Long id);

    User saveUser(User user);

    void deleteUserById(Long id);

    Map<String, Integer> getMostReadGenres(Long userId);

    User updateUser(User user, Long id);
}
