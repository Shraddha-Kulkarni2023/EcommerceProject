package com.example.EcommerceProject.UserMicroService.Configuration;

import com.example.EcommerceProject.UserMicroService.Security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired

    private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for simplicity (typically in REST APIs)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allow unauthenticated access
                        .requestMatchers("/api/user/profile").authenticated()
                        .requestMatchers("/products/**").permitAll() // Allow unauthenticated access
                        .anyRequest().authenticated() // All other requests need authentication
                )



                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)  // ✅ disables login form
                .httpBasic(AbstractHttpConfigurer::disable);// ✅ disables HTTP Basic



        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();*/
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for development/testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // Allow all endpoints
                        .requestMatchers("/products/**").permitAll()
                        .requestMatchers("/inventory/**").permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }




}
