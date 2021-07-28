package com.project.bookstore.service;

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
    public User findUserById(int theId) {
        Optional<User> result = userRepository.findById(theId);
        User theUser = null;

        if (result.isPresent()) {
            theUser = result.get();
        }
        else {
            throw new RuntimeException("Did not find user id - " + theId);
        }
        return theUser;
    }

    @Override
    public void saveUser(User theUser) {
        userRepository.save(theUser);
    }

    @Override
    public void deleteUserById(int theId) {
        userRepository.deleteById(theId);

    }
}
