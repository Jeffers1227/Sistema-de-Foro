package com.example.forum.config;

import com.example.forum.entity.Role;
import com.example.forum.entity.User;
import com.example.forum.repository.RoleRepository;
import com.example.forum.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        return args -> {
            if (roleRepo.findByName("ROLE_USER").isEmpty()) {
                Role userRole = roleRepo.save(new Role("ROLE_USER"));
                
                User user = new User();
                user.setUsername("admin");
                user.setEmail("admin@test.com");
                user.setPassword(encoder.encode("1234")); // Password: 1234
                user.setRoles(Arrays.asList(userRole));
                userRepo.save(user);
                
                User user2 = new User();
                user2.setUsername("pepe");
                user2.setEmail("pepe@test.com");
                user2.setPassword(encoder.encode("1234")); 
                user2.setRoles(Arrays.asList(userRole));
                userRepo.save(user2);
            }
        };
    }
}