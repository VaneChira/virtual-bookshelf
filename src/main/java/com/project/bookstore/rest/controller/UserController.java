package com.project.bookstore.rest.controller;

import com.project.bookstore.exception.UserNotFoundException;
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

    @GetMapping("/getAll")
    public List<User> getUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/getUserById/{userId}")
    public User getUserById(@PathVariable int userId){
        User theUser = userService.findUserById(userId);
        if(theUser == null){
            throw new UserNotFoundException();
        }
        return theUser;
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User theUser){
        theUser.setId(0);
        userService.saveUser(theUser);
        return theUser;
    }

    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User theUser){
        userService.saveUser(theUser);
        return theUser;
    }

    @DeleteMapping("/deleteUserById/{userId}")
    public String deleteUserById(@PathVariable int userId){
       User tempUser = userService.findUserById(userId);
       if(tempUser == null){
           throw new UserNotFoundException();
       }
       userService.deleteUserById(userId);
       return "Deleted user id - " + userId;
    }





}
