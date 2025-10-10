package com;


import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.mysoap.api1.HelloApi;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;



public class HelloSoapClient {
    public static void main(String[] args) {
        // Create CXF Proxy Factory
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(HelloApi.class);
        factory.setAddress("http://localhost:8084/services/HelloApi");

        // Create client proxy
        HelloApi client = (HelloApi) factory.create();
        
     // Configure WS-Security UsernameToken for outbound messages
        Map<String, Object> outProps = new HashMap<>();
        outProps.put("action", "UsernameToken");
        outProps.put("user", "Dhruv");
        outProps.put("passwordType", "PasswordText");
        outProps.put("passwordCallbackClass", ClientPasswordCallback.class.getName());

        Client cxfClient = ClientProxy.getClient(client);
        cxfClient.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));

        // Invoke
        String response = client.sayHello();
        System.out.println("Response from SOAP Service: " + response);

        // Invoke service
       // String response = client.sayHello("Dhruv");

        // Print result
      //  System.out.println("Response from SOAP Service: " + response);
    }
}