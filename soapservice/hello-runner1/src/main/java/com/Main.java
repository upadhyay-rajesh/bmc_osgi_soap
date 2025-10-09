package com;



import com.example.hello.impl.HelloServiceImpl;
import com.mysoap.api1.HelloApi;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;  // OUT on client
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;   // IN on server

import org.apache.cxf.bus.CXFBusFactory;
import org.apache.cxf.Bus;

import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.Bus;




public class Main {
    public static void main(String[] args) throws Exception {
    	/*Bus bus = CXFBusFactory.getDefaultBus();
    	
        // Jetty server
        Server server = new Server(8084);
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);

        // CXF servlet at /services/*
        ServletHolder servletHolder = new ServletHolder(new CXFServlet());
        context.addServlet(servletHolder, "/services/*");
        
     

        // Start Jetty first
        server.start();
        
       
        //Publish SOAP service using CXF factory
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setServiceClass(HelloApi.class);
        factory.setServiceBean(new HelloServiceImpl());
        factory.setAddress("/services/HelloApi");  // relative to Jetty context
        factory.create();
        
     // Add WS-Security UsernameToken validation
        Map<String, Object> inProps = new HashMap<>();
        inProps.put("action", "UsernameToken");
        inProps.put("passwordType", "PasswordText");
        inProps.put("passwordCallbackClass", ServerPasswordCallback.class.getName());
        EndpointImpl endpoint = new EndpointImpl(bus, new HelloServiceImpl());
        endpoint.getInInterceptors().add(new WSS4JInInterceptor(inProps));
        endpoint.publish("/HelloApi");


        factory.getInInterceptors().add(new WSS4JInInterceptor(inProps));

        System.out.println("SOAP Service running at: http://localhost:8084/services/HelloApi?wsdl");

        server.join();
        */
    	 // 1️  Create CXF Bus (ensures interceptors actually work)
        Bus bus = CXFBusFactory.getDefaultBus();

        // 2️  Service implementation
        HelloServiceImpl implementor = new HelloServiceImpl();

        // 3️  Create endpoint on CXF bus
        EndpointImpl endpoint = new EndpointImpl(bus, implementor);

        // 4️  WS-Security inbound properties
        Map<String, Object> inProps = new HashMap<>();
        inProps.put("action", "UsernameToken");
        inProps.put("passwordType", "PasswordText");
        inProps.put("passwordCallbackClass", ServerPasswordCallback.class.getName());

        // 5️  Register interceptor
        endpoint.getInInterceptors().add(new WSS4JInInterceptor(inProps));

        // 6️  Publish service
        String address = "http://localhost:8084/services/HelloApi";
        endpoint.publish(address);

        System.out.println(" Secure SOAP Service running at: " + address + "?wsdl");

        // keep JVM alive
        Thread.currentThread().join();
    }
}
