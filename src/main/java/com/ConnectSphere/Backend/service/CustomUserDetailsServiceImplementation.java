package com.ConnectSphere.Backend.service;


import com.ConnectSphere.Backend.Repository.UserRepository;
import com.ConnectSphere.Backend.config.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // Marks this class as a Spring Service component
public class CustomUserDetailsServiceImplementation implements UserDetailsService {

    @Autowired // Injects an instance of UserRepository
    private UserRepository userRepository;

    @Override // Overrides the method from UserDetailsService interface
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by email (username is typically email in Spring Security for web apps)
        User user = userRepository.findByEmail(username);

        // Check if the user is found and if login_with_google is false
        // The condition `user.isLogin_with_google()` implies you want to handle non-google logins here.
        // If it's a Google login, you might handle it in a separate flow or allow it if not explicitly restricted.
        if (user == null || user.isLogin_with_google()) {
            throw new UsernameNotFoundException("Username not found with email: " + username);
        }
        List<GrantedAuthority> authorities=new ArrayList<>();
        // Convert your com.zosh.model.User to org.springframework.security.core.userdetails.User
        // This Spring Security User constructor requires:
        // 1. username (email)
        // 2. password (encoded password)
        // 3. authorities (a collection of GrantedAuthority objects, usually empty for basic auth or for roles)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),          // Username (email)
                user.getPassword(),       // Password (should be encoded in your database)
                authorities       // Authorities (empty list for now, add roles later if needed)
        );
    }
}