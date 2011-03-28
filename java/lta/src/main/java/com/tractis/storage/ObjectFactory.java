
package com.tractis.storage;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tractis.storage package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ContentIdentifier_QNAME = new QName("http://storage.tractis.com/", "ContentIdentifier");
    private final static QName _Content_QNAME = new QName("http://storage.tractis.com/", "Content");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tractis.storage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ContentRemoveResponse }
     * 
     */
    public ContentRemoveResponse createContentRemoveResponse() {
        return new ContentRemoveResponse();
    }

    /**
     * Create an instance of {@link ProtectionMeasure }
     * 
     */
    public ProtectionMeasure createProtectionMeasure() {
        return new ProtectionMeasure();
    }

    /**
     * Create an instance of {@link Properties }
     * 
     */
    public Properties createProperties() {
        return new Properties();
    }

    /**
     * Create an instance of {@link ContentListRequest }
     * 
     */
    public ContentListRequest createContentListRequest() {
        return new ContentListRequest();
    }

    /**
     * Create an instance of {@link ContentStoreRequest }
     * 
     */
    public ContentStoreRequest createContentStoreRequest() {
        return new ContentStoreRequest();
    }

    /**
     * Create an instance of {@link ContentRecoverResponse }
     * 
     */
    public ContentRecoverResponse createContentRecoverResponse() {
        return new ContentRecoverResponse();
    }

    /**
     * Create an instance of {@link ContentRecoverRequest }
     * 
     */
    public ContentRecoverRequest createContentRecoverRequest() {
        return new ContentRecoverRequest();
    }

    /**
     * Create an instance of {@link ContentRemoveRequest }
     * 
     */
    public ContentRemoveRequest createContentRemoveRequest() {
        return new ContentRemoveRequest();
    }

    /**
     * Create an instance of {@link ProtectedContent }
     * 
     */
    public ProtectedContent createProtectedContent() {
        return new ProtectedContent();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link ContentStoreResponse }
     * 
     */
    public ContentStoreResponse createContentStoreResponse() {
        return new ContentStoreResponse();
    }

    /**
     * Create an instance of {@link ContentListResponse }
     * 
     */
    public ContentListResponse createContentListResponse() {
        return new ContentListResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://storage.tractis.com/", name = "ContentIdentifier")
    public JAXBElement<String> createContentIdentifier(String value) {
        return new JAXBElement<String>(_ContentIdentifier_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://storage.tractis.com/", name = "Content")
    public JAXBElement<byte[]> createContent(byte[] value) {
        return new JAXBElement<byte[]>(_Content_QNAME, byte[].class, null, ((byte[]) value));
    }

}
