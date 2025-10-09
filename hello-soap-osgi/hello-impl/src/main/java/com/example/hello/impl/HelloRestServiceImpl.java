package com.example.hello.impl;

import com.example.hello.api.HelloRestService;

import javax.ws.rs.Path;

@Path("/hello")
public class HelloRestServiceImpl implements HelloRestService {
    @Override
    public String sayHello() {
        return "Hello from OSGi REST!";
    }
}
