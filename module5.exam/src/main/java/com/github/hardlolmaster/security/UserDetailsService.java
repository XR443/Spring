package com.github.hardlolmaster.security;

import com.github.hardlolmaster.repository.RoleRepository;
import com.github.hardlolmaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService
{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserDetailsService(PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            RoleRepository roleRepository)
    {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init()
    {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleRepository.save(adminRole);
        admin.setRoles(Arrays.asList(adminRole));

        userRepository.save(admin);

        User manager1 = new User();
        manager1.setUsername("manager1");
        manager1.setPassword(passwordEncoder.encode("manager1"));
        Role managerRole = new Role();
        managerRole.setName("MANAGER");
        roleRepository.save(managerRole);
        manager1.setRoles(Arrays.asList(managerRole));

        userRepository.save(manager1);

        User manager2 = new User();
        manager2.setUsername("manager2");
        manager2.setPassword(passwordEncoder.encode("manager2"));
        manager2.setRoles(Arrays.asList(managerRole));

        userRepository.save(manager2);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username);
        ArrayList<SimpleGrantedAuthority> list = new ArrayList<>(user.getRoles().size());
        for (Role role : user.getRoles())
        {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), list);
    }
}
