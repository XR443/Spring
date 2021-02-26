package com.github.hardlolmaster.web;

import com.github.hardlolmaster.domain.Post;
import com.github.hardlolmaster.domain.User;
import com.github.hardlolmaster.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class BlogController {
    private PostRepository postRepository;

    @Autowired
    public BlogController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostMapping("/post/create")
    public Mono<Post> createPost(@RequestBody Post post) {

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(User.class)
                .doOnNext(user -> {
                    post.setUser(user);
                })
                .then(postRepository.save(post));
    }

}
