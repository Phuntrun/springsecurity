package com.trgphun.springsecurity.config;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.trgphun.springsecurity.model.Role;
import com.trgphun.springsecurity.model.User;
import com.trgphun.springsecurity.repository.RoleRepository;
import com.trgphun.springsecurity.repository.UserRepository;
import com.trgphun.springsecurity.util.PredifinedRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing Application.....");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
                Role adminRole = roleRepository.save(Role.builder()
                            .name(PredifinedRole.ADMIN_ROLE)
                            .description("Admin role")
                            .build());
                Role userRole = roleRepository.save(Role.builder()
                            .name(PredifinedRole.USE_ROLE)
                            .description("User role")
                            .build());

                var adminRoles = new HashSet<Role>();
                adminRoles.add(adminRole);
                adminRoles.add(userRole);

                userRepository.save(User.builder()
                        .username(ADMIN_USERNAME)
                        .password(bCryptPasswordEncoder.encode(ADMIN_PASSWORD))
                        .roles(adminRoles)
                        .build());

                log.warn("admin user has been created with default password: admin, please change it");          
            }
            log.info("Application initialization completed .....");
        };
    }
}
