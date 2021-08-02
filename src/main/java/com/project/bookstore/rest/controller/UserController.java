package com.project.bookstore.rest.controller;

import com.project.bookstore.model.User;
import com.project.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.findUserById(id);
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping("/updateUser/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id){
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable Long id){
       userService.deleteUserById(id);
    }
}
