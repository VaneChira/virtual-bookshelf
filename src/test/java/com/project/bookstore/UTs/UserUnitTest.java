package com.project.bookstore.UTs;

import com.project.bookstore.BaseTest;
import com.project.bookstore.model.User;
import com.project.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserUnitTest extends BaseTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void when_userRegister_then_countIncreases() {
        User testUser = new User();
        int countAll = userRepository.findAll().size();
        User registeredUser = userRepository.save(testUser);

        int countAfterRegister = userRepository.findAll().size();
        assert (countAll + 1 == countAfterRegister);

        userRepository.deleteById(registeredUser.getId());
        int countAfterDelete = userRepository.findAll().size();
        assert (countAll == countAfterDelete);
    }


    @Test
    void when_updateUserName_then_nameGetsUpdated() {
        User user = userRepository.findById(USER_ID).get();
        String originalUserName = user.getName();
        user.setName("new name");
        User updatedUserName = userRepository.save(user);
        assert (!updatedUserName.getName().equals(originalUserName));

        updatedUserName.setName("Jessica");
        userRepository.save(updatedUserName);
    }
}
