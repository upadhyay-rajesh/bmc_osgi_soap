
package com.mysoap.api1;


import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;
import java.io.IOException;

@WebService
public interface HelloApi {
	 @WebMethod
    String sayHello();
	 
    @WebMethod
    String uploadFile(
        @WebParam(name = "fileName") String fileName,
        @WebParam(name = "fileData") @XmlMimeType("application/octet-stream") DataHandler fileData
    ) throws IOException;

    // ✅ Download attachment
    @WebMethod
    @XmlMimeType("application/octet-stream")
    DataHandler downloadFile(@WebParam(name = "fileName") String fileName) throws IOException;
}

