package com.example.EcommerceProject.UserMicroService.Service;

import com.example.EcommerceProject.UserMicroService.Model.RegisterRequest;
import com.example.EcommerceProject.UserMicroService.Model.ProjectUser;
import com.example.EcommerceProject.UserMicroService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Primary
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProjectUser saveUser(ProjectUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /*public boolean validateUser(String username, String rawpassword) {

        User optionalUser = userRepository.findByUsername(username);
        if(!optionalUser == null) {

            User user = optionalUser.getPassword();
            return passwordEncoder.matches(rawpassword, user.getPassword());
        }

        return false;
    }*/

    public void register(RegisterRequest registerRequest) {

        if(userRepository.existsByUsername(registerRequest.getUsername())) {

            throw new RuntimeException("Username already taken");
        }

        ProjectUser user = new ProjectUser();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
    }

    public ProjectUser authenticate(String username, String password) {

        ProjectUser user = userRepository.findByUsername(username);

        if(user == null) {

            throw new RuntimeException("User not found");
        }

        if(!user.getPassword().equals(password)) {

            throw new RuntimeException("Invalid Password");
        }
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ProjectUser user = userRepository.findByUsername(username);
        if(user == null) {

            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    public ProjectUser getCurrentUser(String username) {

        if(userRepository.findByUsername(username) == null) {

            return null;
        }
        return userRepository.findByUsername(username);
    }
}
