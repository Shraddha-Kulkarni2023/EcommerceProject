package com.example.EcommerceProject.UserMicroService.Service;

import com.example.EcommerceProject.UserMicroService.Model.ProjectUser;
import com.example.EcommerceProject.UserMicroService.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ProjectUser projectUser = userRepository.findByUsername(username);

        // If user is not found, throw exception
        if (projectUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Return custom UserDetails object
        return (UserDetails) new CustomUserDetailsService((UserRepository) projectUser);
    }
}

