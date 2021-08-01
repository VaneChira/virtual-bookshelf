package com.project.bookstore.security;

import com.project.bookstore.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserSecurityService extends UserDetailsService {
    User save(UserRegistrationFormEntity userRegistrationFormEntity);
}
