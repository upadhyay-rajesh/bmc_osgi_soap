package com;


import com.example.hello.impl.HelloServiceImpl;
//import com.example.hello.security.ServerPasswordCallback;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.BusFactory;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.ws.Endpoint;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

public class MainSSL {

    public static void main(String[] args) throws Exception {
        // 🔹 Load keystore
        String keystorePath = "server-keystore.jks";
        String password = "changeit";

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keystorePath), password.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, password.toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        // 🔹 Create Jetty HTTPS server (external to CXF)
        Server server = new Server();

        HttpConfiguration httpsConfig = new HttpConfiguration();
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setSslContext(sslContext);

        ServerConnector sslConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(httpsConfig));
        sslConnector.setPort(8443);

        server.setConnectors(new Connector[]{sslConnector});

        // 🔹 Setup CXF servlet (this avoids CXF engine registration)
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);

        CXFServlet cxfServlet = new CXFServlet();
        context.addServlet(new ServletHolder(cxfServlet), "/services/*");

        // 🔹 Start Jetty HTTPS
        server.start();

        // 🔹 Register SOAP endpoint on HTTPS bus
        HelloServiceImpl implementor = new HelloServiceImpl();
        EndpointImpl endpoint = new EndpointImpl(BusFactory.getDefaultBus(), implementor);

        /*Map<String, Object> inProps = new HashMap<>();
        inProps.put("action", "UsernameToken");
        inProps.put("passwordType", "PasswordText");
        inProps.put("passwordCallbackClass", ServerPasswordCallback.class.getName());
        endpoint.getInInterceptors().add(new WSS4JInInterceptor(inProps));
*/
        endpoint.publish("/HelloApi");

        System.out.println("✅ HTTPS SOAP Service running at: https://localhost:8443/services/HelloApi?wsdl");

        server.join();
    }
}
