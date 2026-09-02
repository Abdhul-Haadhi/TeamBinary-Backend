package com.example.TeamBinary_Backend.Security;

import com.example.TeamBinary_Backend.Entities.UserEntity;
import com.example.TeamBinary_Backend.Repositories.UserRepository;
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


    // Standard Spring Security method
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        throw new UsernameNotFoundException("Organization is required to load this user");
    }

    // Organization-aware user loading
    public UserPrincipal loadUserByEmailAndOrganization(String email, String organizationId) {

        UserEntity user = userRepository.findByEmailAndOrganizationId(email, organizationId).orElseThrow(() ->
                                        new UsernameNotFoundException("User not found in this organization"));

        return new UserPrincipal(user);
    }
}
