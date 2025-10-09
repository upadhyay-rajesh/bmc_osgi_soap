package com.example.hello.impl;

import javax.jws.WebService;

import com.mysoap.api1.HelloApi;




@WebService(endpointInterface = "com.mysoap.api1.HelloApi")
public class HelloServiceImpl implements HelloApi {
    @Override
    public String sayHello() {
        return "Hello,  from OSGi SOAP!";
    }
}
