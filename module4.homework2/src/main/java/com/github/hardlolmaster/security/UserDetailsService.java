package com.github.hardlolmaster.security;

import com.github.hardlolmaster.domain.Role;
import com.github.hardlolmaster.domain.User;
import com.github.hardlolmaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        admin.setRoles(Arrays.asList(adminRole));

        userRepository.save(admin);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        Role userRole = new Role();
        userRole.setName("USER");
        user.setRoles(Arrays.asList(userRole));

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new Principal(user);
    }
}
