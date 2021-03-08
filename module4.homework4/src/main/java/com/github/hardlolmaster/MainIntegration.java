package com.github.hardlolmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Executors;

@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
public class MainIntegration {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainIntegration.class);

        UpdateService updateService = context.getBean(UpdateService.class);
        User user1 = new User("username", "email");
        System.out.println(user1);
        User user = updateService.updateUser(user1);
        System.out.println(user);
        System.out.println(user1);
        Collection<User> users = updateService.updateUsers(
                Arrays.asList(new User("username1", "email1"), new User("username2", "email2")));
        System.out.println(users);

        DirectChannel directChannel = context.getBean("testDirectChannel", DirectChannel.class);
        directChannel.subscribe(message -> {
            System.out.println(message.getPayload());
        });

        directChannel.send(MessageBuilder.withPayload(new User("toChannelUsername", "toChannelEmail")).build());

        context.close();
    }

    @Bean("testDirectChannel")
    public DirectChannel directChannel() {
        return MessageChannels.direct().get();
    }

    @Bean("update.user")
    public IntegrationFlow updateUser(OtherService otherService) {
        return flow -> flow
                .log()
                .<User, User>transform(source -> {
                    source.setUsername("transformMethodUsername");
                    return source;
                })
                .log()
                .transform(otherService, "other")
                .log()
                .bridge();
    }

    @Bean("update.users")
    public IntegrationFlow updateUsers(OtherService otherService) {
        return flow -> flow
                .log()
                .split()
                .log()
                .channel(MessageChannels.executor(Executors.newSingleThreadExecutor()))
                .<User, User>transform(source -> {
                    source.setUsername("transformMethodUsernameList");
                    return source;
                })
                .log()
                .transform(otherService, "other")
                .log()
                .bridge();
    }
}
