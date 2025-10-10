package com.example.hello.impl;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.mysoap.api1.HelloApi;




@WebService(endpointInterface = "com.mysoap.api1.HelloApi")
public class HelloServiceImpl implements HelloApi {
    @Override
    public String sayHello() {
        return "Hello,  from OSGi SOAP!";
    }
    
    @Override
    public String uploadFile(String fileName, DataHandler fileData) {
        try (InputStream in = fileData.getInputStream();
             OutputStream out = new FileOutputStream(new File("uploads", fileName))) {
            new File("uploads").mkdirs();
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
            return "✅ File '" + fileName + "' uploaded successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Upload failed: " + e.getMessage();
        }
    }

    @Override
    public DataHandler downloadFile(String fileName) {
        File file = new File("uploads", fileName);
        if (!file.exists()) throw new RuntimeException("File not found: " + file.getAbsolutePath());
        DataSource source = new FileDataSource(file);
        return new DataHandler(source);
    }
}
