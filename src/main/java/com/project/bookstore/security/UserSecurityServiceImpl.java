package com.project.bookstore.security;

import com.project.bookstore.model.Role;
import com.project.bookstore.model.User;
import com.project.bookstore.repository.RoleRepository;
import com.project.bookstore.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.List.of;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserSecurityServiceImpl(UserRepository userRepo, RoleRepository roleRepo, BCryptPasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepo;
        this.roleRepository = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(UserRegistrationFormEntity userRegistrationFormEntity) {
        final var user = new User();
        user.setName(userRegistrationFormEntity.getFirstName());
        user.setLastName(userRegistrationFormEntity.getLastName());
        user.setEmail(userRegistrationFormEntity.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationFormEntity.getPassword()));
        user.setRoles(of(roleRepository.findByName(RoleName.ROLE_USER)));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final var user = userRepository.findByEmail(email);
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
