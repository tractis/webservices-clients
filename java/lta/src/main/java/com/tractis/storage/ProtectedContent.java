
package com.tractis.storage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://storage.tractis.com/}Content"/>
 *         &lt;element ref="{http://storage.tractis.com/}ContentIdentifier"/>
 *         &lt;element ref="{http://storage.tractis.com/}Properties"/>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element ref="{http://storage.tractis.com/}ProtectionMeasure"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content",
    "contentIdentifier",
    "properties",
    "protectionMeasure"
})
@XmlRootElement(name = "ProtectedContent")
public class ProtectedContent {

    @XmlElement(name = "Content", required = true)
    protected byte[] content;
    @XmlElement(name = "ContentIdentifier", required = true)
    protected String contentIdentifier;
    @XmlElement(name = "Properties", required = true)
    protected Properties properties;
    @XmlElement(name = "ProtectionMeasure", required = true, type = ProtectionMeasure.class)
    protected List<ProtectionMeasure> protectionMeasure;

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setContent(byte[] value) {
        this.content = ((byte[]) value);
    }

    /**
     * Gets the value of the contentIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentIdentifier() {
        return contentIdentifier;
    }

    /**
     * Sets the value of the contentIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentIdentifier(String value) {
        this.contentIdentifier = value;
    }

    /**
     * Gets the value of the properties property.
     * 
     * @return
     *     possible object is
     *     {@link Properties }
     *     
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Sets the value of the properties property.
     * 
     * @param value
     *     allowed object is
     *     {@link Properties }
     *     
     */
    public void setProperties(Properties value) {
        this.properties = value;
    }

    /**
     * Gets the value of the protectionMeasure property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the protectionMeasure property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtectionMeasure().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProtectionMeasure }
     * 
     * 
     */
    public List<ProtectionMeasure> getProtectionMeasure() {
        if (protectionMeasure == null) {
            protectionMeasure = new ArrayList<ProtectionMeasure>();
        }
        return this.protectionMeasure;
    }

}
