package com.github.hardlolmaster.utils;

import com.github.hardlolmaster.domain.Post;
import com.github.hardlolmaster.repository.PostRepository;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.ArrayList;
import java.util.List;

public class CommandGetAllPosts extends HystrixCommand<List<Post>> {
    private final PostRepository postRepository;

    public CommandGetAllPosts(PostRepository postRepository) {
        super(HystrixCommandGroupKey.Factory.asKey("GetAllPosts"));
        this.postRepository = postRepository;
    }

    @Override
    protected List<Post> run() throws Exception {
        return postRepository.findAll();
    }

    @Override
    protected List<Post> getFallback() {
        return new ArrayList<>();
    }
}
