package com.github.hardlolmaster.web;

import com.github.hardlolmaster.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Controller
public class PageController {

    private final PostRepository postRepository;

    @Autowired
    public PageController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public String posts(Mono<Principal> principalMono, Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "posts";
    }

    @GetMapping("/creatingPost")
    public String creatingPost(Mono<Principal> principalMono, Model model) {
        return "creatingPost";
    }
}
