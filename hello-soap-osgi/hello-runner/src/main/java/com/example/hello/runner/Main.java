/*package com.example.hello.runner;

import com.example.hello.impl.HelloRestServiceImpl;


import java.util.Collections;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import org.eclipse.jetty.servlet.*;


public class Main {
    public static void main(String[] args) throws Exception {
        // Jetty server
      /*  Server server = new Server(8087);
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);
        
        //context.adds

        // Add CXF servlet
        context.addServlet(new ServletHolder(new CXFServlet()), "/services/*");
        
        

        server.start();

        JAXRSServerFactoryBean restFactory = new JAXRSServerFactoryBean();
        restFactory.setAddress("/services");
        restFactory.setServiceBeans(Collections.singletonList(new HelloRestServiceImpl()));
        restFactory.create();

        System.out.println("REST Service running at: http://localhost:8080/services/hello");

        server.join();
        */
    	
   // }
//}
 package com.example.hello.runner;

import com.example.hello.impl.HelloRestServiceImpl;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import java.util.Collections;

public class Main {
    public static void main(String[] args) throws Exception {
        // Publish REST service directly with CXF HTTP transport
        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setAddress("http://localhost:8087/services");
        factory.setServiceBeans(Collections.singletonList(new HelloRestServiceImpl()));
        factory.create();

        System.out.println("REST Service running at: http://localhost:8087/services/hello");

        // Keep JVM alive
        Thread.currentThread().join();
    }
}
