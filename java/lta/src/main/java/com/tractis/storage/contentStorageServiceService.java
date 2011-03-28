
package com.tractis.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import org.springframework.core.io.ClassPathResource;

@WebServiceClient(targetNamespace = "http://storage.tractis.com/", name = "contentStorageService", wsdlLocation = "file%3A%2Fhome%2Fdave%2Flta-workspace%2Flta%2Fstorage-client%2Ftarget%2Fclasses%2Fwsdl%2Fcontent_storage.wsdl")
public class contentStorageServiceService
    extends Service
{

    private static Map ports = new HashMap();
    public static Class SOAPport = com.tractis.storage.SOAPport.class;

    static {
        ports.put(new QName("http://storage.tractis.com/", "contentStorageServiceLocalPort"), SOAPport);
        ports.put(new QName("http://storage.tractis.com/", "contentStoragePortSoap"), SOAPport);
    }

    public contentStorageServiceService()
        throws IOException
    {
        super(new ClassPathResource("wsdl/content_storage.wsdl").getURL(), new QName("http://storage.tractis.com/", "contentStorageService"));
    }

    public static Map getPortClassMap() {
        return ports;
    }

    @WebEndpoint(name = "contentStorageServiceLocalPort")
    public com.tractis.storage.SOAPport getcontentStorageServiceLocalPort() {
        return ((com.tractis.storage.SOAPport)(this).getPort(new QName("http://storage.tractis.com/", "contentStorageServiceLocalPort"), SOAPport));
    }

    @WebEndpoint(name = "contentStoragePortSoap")
    public com.tractis.storage.SOAPport getcontentStoragePortSoap() {
        return ((com.tractis.storage.SOAPport)(this).getPort(new QName("http://storage.tractis.com/", "contentStoragePortSoap"), SOAPport));
    }

}
