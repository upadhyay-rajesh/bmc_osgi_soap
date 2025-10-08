package com.example.hello.impl;

import com.example.hello.api.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        if (name == null || name.isBlank()) return "Hello, world!";
        return "Hello, " + name + "!";
    }
}
