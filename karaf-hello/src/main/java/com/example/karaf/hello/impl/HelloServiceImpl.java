package com.example.karaf.hello.impl;

import com.example.karaf.hello.api.HelloService;
import com.example.karaf.hello.api.Student;

import org.osgi.service.component.annotations.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Component(service = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String say(String name) {
    	try {
    	// 1. Create JAXBContext for generated Student class
        JAXBContext context = JAXBContext.newInstance(Student.class);

        // 2. Create a Student object
        Student student = new Student();
        student.setId(101);
        student.setName("Dhruv Upadhyay");
        student.setEmail("dhruv@example.com");

        LocalDate dob = LocalDate.of(2000, 5, 15);
        Date dobDate = Date.from(dob.atStartOfDay(ZoneId.systemDefault()).toInstant());
        student.setDob(dobDate);

        // 3. Marshalling (Java → XML)
        File file = new File("student.xml");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(student, file); 
        marshaller.marshal(student, System.out);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
        System.out.println("\n student.xml created successfully!");
        if (name == null || name.isBlank()) name = "World";
        return "Hello, " + name + "!";
    }
}
