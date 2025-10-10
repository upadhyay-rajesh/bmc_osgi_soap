package com;


import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.mysoap.api1.HelloApi;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.File;



public class SoapClentSSL {
    public static void main(String[] args) {
    	System.setProperty("javax.net.ssl.trustStore", "server-keystore.jks");
    	System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        // Create CXF Proxy Factory
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(HelloApi.class);
        factory.setAddress("https://localhost:8084/services/HelloApi");

        // Create client proxy
        HelloApi client = (HelloApi) factory.create();
        
        //  Enable MTOM
        ((org.apache.cxf.jaxws.JaxWsProxyFactoryBean) factory).getProperties().put("mtom-enabled", true);

        
     // Configure WS-Security UsernameToken for outbound messages
     /*   Map<String, Object> outProps = new HashMap<>();
        outProps.put("action", "UsernameToken");
        outProps.put("user", "Dhruv");
        outProps.put("passwordType", "PasswordText");
        outProps.put("passwordCallbackClass", ClientPasswordCallback.class.getName());

        Client cxfClient = ClientProxy.getClient(client);
        cxfClient.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
*/
        // Invoke
        String response = client.sayHello();
        System.out.println("Response from SOAP Service: " + response);
        
    
        File file = new File("test-upload.txt");
        try {
            java.nio.file.Files.write(file.toPath(), "This is a test SOAP attachment.".getBytes());
            DataHandler dh = new DataHandler(new FileDataSource(file));
            System.out.println(client.uploadFile("test-upload.txt", dh));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  Download same file
        try {
            DataHandler handler = client.downloadFile("test-upload.txt");
            handler.writeTo(new java.io.FileOutputStream("downloaded.txt"));
            System.out.println(" File downloaded to: downloaded.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Invoke service
       // String response = client.sayHello("Dhruv");

        // Print result
      //  System.out.println("Response from SOAP Service: " + response);
    }
}