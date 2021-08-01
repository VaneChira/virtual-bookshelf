package com.project.bookstore.security;

import com.project.bookstore.model.Role;
import com.project.bookstore.model.User;
import com.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserSecurityServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationFormEntity userRegistrationFormEntity) {
        User user = new User();
        user.setName(userRegistrationFormEntity.getFirstName());
        user.setLastName(userRegistrationFormEntity.getLastName());
        user.setEmail(userRegistrationFormEntity.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationFormEntity.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority>
    mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new
                SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


}
