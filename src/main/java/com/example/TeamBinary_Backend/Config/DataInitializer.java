package com.example.TeamBinary_Backend.Config;

import com.example.TeamBinary_Backend.Entities.OrganizationEntity;
import com.example.TeamBinary_Backend.Entities.UserEntity;
import com.example.TeamBinary_Backend.Repositories.OrganizationRepository;
import com.example.TeamBinary_Backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(
            OrganizationRepository organizationRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
                           ){
        return args -> {

            // SYSTEM ADMIN ORGANIZATION
            OrganizationEntity organization = organizationRepository.findByOrganizationCode("SYSTEM-AUTOTRACE").orElseGet(() -> {

                                OrganizationEntity org = new OrganizationEntity();

                                org.setOrganizationCode("SYSTEM-AUTOTRACE");
                                org.setOrganizationName("AutoTrace System Administration");
                                org.setAddress("System Level");
                                org.setActive(true);

                                return organizationRepository.save(org);
                            });

            // SYSTEM ADMIN
            if (!userRepository.existsByEmailAndOrganizationId("sysadmin@autotrace.com", organization.getId())) {

                UserEntity systemAdmin = new UserEntity();

                systemAdmin.setEmail("sysadmin@autotrace.com");
                systemAdmin.setPassword(passwordEncoder.encode("SystemAdmin@123"));
                systemAdmin.setRole("SYSTEM_ADMIN");
                systemAdmin.setOrganization(organization);

                userRepository.save(systemAdmin);

                System.out.println("========================================");
                System.out.println("Default SYSTEM ADMIN created.");
                System.out.println("Organization Code: SYSTEM-AUTOTRACE");
                System.out.println("Email: sysadmin@autotrace.com");
                System.out.println("Password: SystemAdmin@123");
                System.out.println("Role: SYSTEM_ADMIN");
                System.out.println("========================================");
            }

        };
    }
}
