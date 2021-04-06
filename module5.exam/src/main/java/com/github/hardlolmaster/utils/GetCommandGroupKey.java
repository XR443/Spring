package com.github.hardlolmaster.utils;

import com.netflix.hystrix.HystrixCommandGroupKey;

public class GetCommandGroupKey {
    public static HystrixCommandGroupKey getFor(String name){
        return HystrixCommandGroupKey.Factory.asKey(name);
    }
}
