package com.github.hardlolmaster.web;

import com.github.hardlolmaster.hystrix.CommandGetAllPosts;
import com.github.hardlolmaster.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final PostRepository postRepository;

    @Autowired
    public PageController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public String posts(Model model) {
//        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("posts", new CommandGetAllPosts(postRepository).execute());
        return "posts";
    }

    @GetMapping("/creatingPost")
    @PreAuthorize("isAuthenticated()")
    public String creatingPost(Model model) {
        return "creatingPost";
    }
}
