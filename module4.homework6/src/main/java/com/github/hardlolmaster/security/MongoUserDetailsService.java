package com.github.hardlolmaster.security;

import com.github.hardlolmaster.domain.User;
import com.github.hardlolmaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class MongoUserDetailsService implements ReactiveUserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public MongoUserDetailsService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        User admin = new User();
        admin.setAuthority(Arrays.asList(new SimpleGrantedAuthority("ROLE_" + "admin")));
        admin.setEnabled(true);
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setAccountNonLocked(true);
        admin.setAccountNonExpired(true);
        admin.setCredentialsNonExpired(true);

        userRepository.findByUsername("admin").switchIfEmpty(userRepository.save(admin)).subscribe();

        User user = new User();
        user.setAuthority(Arrays.asList(new SimpleGrantedAuthority("ROLE_" + "user")));
        user.setEnabled(true);
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);

        userRepository.findByUsername("user").switchIfEmpty(userRepository.save(user)).subscribe();
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username).map(user -> (UserDetails) user);
    }
}
