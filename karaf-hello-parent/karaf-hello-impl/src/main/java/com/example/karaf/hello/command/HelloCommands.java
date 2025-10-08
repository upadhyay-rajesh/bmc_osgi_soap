package com.example.karaf.hello.command;

import com.example.karaf.hello.api.HelloService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
    service = HelloCommands.class,
    property = {
        "osgi.command.scope=hello",
        "osgi.command.function=say",
        "osgi.command.function=upper",
        "osgi.command.function=count"
    }
)
public class HelloCommands {

    private HelloService hello;

    @Reference
    void setHello(HelloService hello) { this.hello = hello; }

    public String say(String name) { return hello.say(name); }
    public String upper(String text) { return hello.upper(text); }
    public int count(String text) { return hello.count(text); }
}
