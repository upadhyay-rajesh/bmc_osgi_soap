package com.example.karaf.hello.command;

import com.example.karaf.hello.api.HelloService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Registers Gogo commands: hello:say <name>
 */
@Component(
    service = HelloCommands.class,
    property = {
        "osgi.command.scope=hello",
        "osgi.command.function=say"
    }
)
public class HelloCommands {

    private HelloService hello;

    @Reference
    void setHello(HelloService hello) { this.hello = hello; }

    public String say(String name) {
        String msg = hello.say(name);
        System.out.println(msg); // print + return
        return msg;
    }
}
