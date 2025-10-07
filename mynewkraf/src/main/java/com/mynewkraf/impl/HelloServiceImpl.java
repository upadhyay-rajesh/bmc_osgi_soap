package com.mynewkraf.impl;

import org.osgi.service.component.annotations.Component;

import com.mynewkraf.api.HelloService;

@Component(service = HelloService.class)
public class HelloServiceImpl implements HelloService{

	@Override
	public String say(String name) {
		// TODO Auto-generated method stub
		 return "Hello, " + name + "!";
	}

}
