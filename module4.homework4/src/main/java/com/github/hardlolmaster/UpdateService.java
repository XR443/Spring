package com.github.hardlolmaster;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Collection;

@MessagingGateway
public interface UpdateService {
    @Gateway(requestChannel = "update.user.input")
    User updateUser(User user);

    @Gateway(requestChannel = "update.users.input")
    Collection<User> updateUsers(Collection<User> user);
}
