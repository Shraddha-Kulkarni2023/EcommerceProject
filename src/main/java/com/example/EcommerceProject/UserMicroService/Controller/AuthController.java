package com.example.EcommerceProject.UserMicroService.Controller;

import com.example.EcommerceProject.UserMicroService.Model.AuthResponse;
import com.example.EcommerceProject.UserMicroService.Model.RegisterRequest;
import com.example.EcommerceProject.UserMicroService.Model.ProjectUser;
import com.example.EcommerceProject.UserMicroService.Repository.UserRepository;
import com.example.EcommerceProject.UserMicroService.Security.JwtUtil;
import com.example.EcommerceProject.UserMicroService.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from Auth Service");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {

        /*System.out.println("Register endpoint hit");
        userService.register(registerRequest);
        return ResponseEntity.ok("User Registered Successfully");*/
        ProjectUser projectUser = new ProjectUser();
        projectUser.setUsername(registerRequest.getUsername());
        projectUser.setPassword(registerRequest.getPassword());
        projectUser.setEmail(registerRequest.getEmail());
        //projectUser.setRole(registerRequest.getRole());
        projectUser.setRole("User");

        if (userRepository.existsByUsername(projectUser.getUsername())) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error : User is already taken!!!");


        }



        if(projectUser.getUsername() == null || projectUser.getUsername().isEmpty() ||
        projectUser.getPassword() == null || projectUser.getPassword().isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: Username and Password cannot be empty!");
        }

        //System.out.println("Saving user: " + projectUser);
        logger.info("RegisterRequest received: {}", projectUser);

        userRepository.save(projectUser);
        return ResponseEntity.ok("User Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody RegisterRequest loginRequest) {

        ProjectUser user = userService.authenticate(loginRequest.getUsername(),loginRequest.getPassword());

        String token = jwtUtil.generateToken(user.getUsername());
        //System.out.println("Generated token: " + token);  // Print the token for debugging
        logger.info("Generated token: " + token);


        return ResponseEntity.ok(new AuthResponse(token));

    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile() {
        return ResponseEntity.ok("This is your profile data.");
    }

    @GetMapping("/me")
    public  ResponseEntity<ProjectUser> getCurrentUser(HttpServletRequest httpServletRequest) {

        String token = extractTokenFromRequest(httpServletRequest);
        String username = jwtUtil.extractUsername(token);

        /*ProjectUser user = userService.getCurrentUser(username);
        return ResponseEntity.ok(user);*/
        ProjectUser user = userService.getCurrentUser(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(user);



    }

    private String extractTokenFromRequest(HttpServletRequest httpServletRequest) {

        String header = httpServletRequest.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer")) {

            return header.substring(7);
        }
        throw new RuntimeException("JWT token is missing");

    }

}







