package com.example.karaf.hello.impl;

import com.example.karaf.hello.api.HelloService;
import com.example.karaf.hello.entity.Greeting;
import jakarta.persistence.*;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = HelloService.class)
public class HelloServiceImpl implements HelloService {

   // private static final Logger log = LoggerFactory.getLogger(HelloServiceImpl.class);

   // private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("helloPU");
    /*private EntityManagerFactory emf;

    @Activate
    public void activate() {
        emf = Persistence.createEntityManagerFactory("helloPU");
    }*/
    
	@PersistenceUnit(unitName = "helloPU")
    private EntityManagerFactory emf;

    @Activate
    public void activate() {
        System.out.println("HelloServiceImpl activated, emf = " + emf);
    }

    @Override
    public String say(String name) {
        if (name == null || name.isBlank()) name = "World";
        String msg = "Hello, " + name + "!";
        return msg;
    }

    @Override
    public String upper(String text) {
        return text == null ? "" : text.toUpperCase();
    }

    @Override
    public int count(String text) {
        return text == null ? 0 : text.length();
    }
}
