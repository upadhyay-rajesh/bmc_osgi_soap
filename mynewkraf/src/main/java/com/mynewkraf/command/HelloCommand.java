package com.mynewkraf.command;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.mynewkraf.api.Employee;
import com.mynewkraf.api.HelloService;

@Component(service = HelloCommand.class, property = { "osgi.command.scope=hello", "osgi.command.function=say",
		"osgi.command.function=addRecord", "osgi.command.function=deleteRecord", "osgi.command.function=editRecord",
		"osgi.command.function=viewRecord", "osgi.command.function=viewAllRecord" })
public class HelloCommand {

	private HelloService hello;

	@Reference
	void setHello(HelloService hello) {
		this.hello = hello;
	}

	public String say(String name) {
		String msg = hello.say(name);
		System.out.println(msg); // print + return
		return msg;
	}

	public String addRecord(String name,String password,String email,String address) {
		Employee e1=new Employee();
		e1.setName(name);
		e1.setPassword(password);
		e1.setEmail(email);
		e1.setAddress(address);
		String str=hello.createProfile(e1);
		
		return str;
	}

	public String deleteRecord(String email) {
		String str=hello.deleteProfile(email);
		return str;
	}

	public String editRecord(String name,String password,String email,String address) {
		Employee e1=new Employee();
		e1.setName(name);
		e1.setPassword(password);
		e1.setEmail(email);
		e1.setAddress(address);
		
		String str=hello.editProfile(email, e1);
		return str;

	}

	public String viewRecord(String email) {
		String str=hello.viewProfile(email);
		return str;
	}

	public List<Employee> viewAllRecord() {
		List<Employee> ee=hello.viewAllProfile();
		return ee;
	}

}













