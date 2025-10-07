package com.mynewkraf.command;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.mynewkraf.api.HelloService;





@Component(
	    service = HelloCommand.class,
	    property = {
	        "osgi.command.scope=hello",
	        "osgi.command.function=say"
	    }
	)
public class HelloCommand {
	
	 private HelloService hello;

	    @Reference
	    void setHello(HelloService hello) { this.hello = hello; }

	    public String say(String name) {
	        String msg = hello.say(name);
	        System.out.println(msg); // print + return
	        return msg;
	    }

}
