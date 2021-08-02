package com.project.bookstore.service;

import com.project.bookstore.exception.ResourceNotFoundException;
import com.project.bookstore.model.User;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> result = userRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new ResourceNotFoundException("Did not find user id - " + id , User.class.getSimpleName());
        }
    }

    @Override
    public User saveUser(User user) {
       return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            userRepository.deleteById(id);
        }
        else {
            throw new ResourceNotFoundException("Did not find user id - " + id, User.class.getSimpleName());
        }
    }

    @Override
    public User updateUser(User user, Long id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            User updatedUser = user;
            updatedUser.setId(id);
            return userRepository.save(updatedUser);
        }
        else {
            throw new ResourceNotFoundException("Did not find user id - " + id, User.class.getSimpleName());
        }
    }
}
