package com.project.bookstore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserSecurityService userSecurityService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationSuccessHandler loginSuccessHandler;

    public SecurityConfiguration(UserSecurityService userSecurityService, BCryptPasswordEncoder passwordEncoder, AuthenticationSuccessHandler loginSuccessHandler) {
        this.userSecurityService = userSecurityService;
        this.passwordEncoder = passwordEncoder;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
                        "/registration**",
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/books/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/books/**").hasAuthority(RoleName.ROLE_ADMIN)
                .antMatchers(HttpMethod.PUT, "/api/books/**").hasAuthority(RoleName.ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/api/books/**").hasAuthority(RoleName.ROLE_ADMIN)
                .antMatchers("/admin/**").hasAuthority(RoleName.ROLE_ADMIN)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(loginSuccessHandler)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final var auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userSecurityService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
