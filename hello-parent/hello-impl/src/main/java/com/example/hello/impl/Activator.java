package com.example.hello.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.aegis.databinding.AegisDatabinding;

import com.example.hello.api.HelloService;

public class Activator implements BundleActivator {

    private Server server;

    @Override
    public void start(BundleContext context) throws Exception {
        // Publish a simple SOAP (Aegis data binding) endpoint at http://localhost:8181/hello
        HelloService serviceImpl = new HelloServiceImpl();

        ServerFactoryBean sf = new ServerFactoryBean();
        sf.setServiceClass(HelloService.class);
        sf.setServiceBean(serviceImpl);
        sf.setAddress("http://0.0.0.0:8181/hello");
        sf.setDataBinding(new AegisDatabinding()); // keeps deps minimal; no JAXB required

        server = sf.create();
        System.out.println("[hello-impl] SOAP endpoint started at http://localhost:8181/hello  (WSDL at ?wsdl)");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (server != null) {
            server.stop();
            server.destroy();
            server = null;
        }
        System.out.println("[hello-impl] SOAP endpoint stopped.");
    }
}
