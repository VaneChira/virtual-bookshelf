package com.project.bookstore.UTs;

import com.project.bookstore.BaseTest;
import com.project.bookstore.model.User;
import com.project.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserUnitTest extends BaseTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void when_userRegister_then_countIncreases() {
        final var countAll = userRepository.findAll().size();
        final var registeredUser = userRepository.save(new User());

        final var countAfterRegister = userRepository.findAll().size();
        assert (countAll + 1 == countAfterRegister);

        userRepository.deleteById(registeredUser.getId());
        final var countAfterDelete = userRepository.findAll().size();
        assert (countAll == countAfterDelete);
    }

    @Test
    void when_updateUserName_then_nameGetsUpdated() {
        final var user = userRepository.findById(USER_ID).get();
        final var originalUserName = user.getName();
        user.setName("new name");
        final var updatedUserName = userRepository.save(user);
        assert (!updatedUserName.getName().equals(originalUserName));

        updatedUserName.setName("Jessica");
        userRepository.save(updatedUserName);
    }
}
