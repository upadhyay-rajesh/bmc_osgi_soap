package com.example.karaf.hello.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "greetings")
public class Greeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String message;

    public Greeting() {}
    public Greeting(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
