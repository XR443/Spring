package com.github.hardlolmaster;

import org.springframework.stereotype.Service;

@Service
public class OtherService {
    public User other(User user) {
        user.setUsername("otherBeanMethodUsername");
        return user;
    }
}
