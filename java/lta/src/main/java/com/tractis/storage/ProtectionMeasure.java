
package com.tractis.storage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="ProtectionContent" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="ProtectionType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ApplicationDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="ExpirationDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
    "protectionContent",
    "protectionType",
    "applicationDate",
    "expirationDate"
})
@XmlRootElement(name = "ProtectionMeasure")
public class ProtectionMeasure {

    @XmlElement(name = "ProtectionContent", required = true)
    protected byte[] protectionContent;
    @XmlElement(name = "ProtectionType", required = true)
    protected String protectionType;
    @XmlElement(name = "ApplicationDate", required = true)
    protected XMLGregorianCalendar applicationDate;
    @XmlElement(name = "ExpirationDate", required = true)
    protected XMLGregorianCalendar expirationDate;

    /**
     * Gets the value of the protectionContent property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getProtectionContent() {
        return protectionContent;
    }

    /**
     * Sets the value of the protectionContent property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setProtectionContent(byte[] value) {
        this.protectionContent = ((byte[]) value);
    }

    /**
     * Gets the value of the protectionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtectionType() {
        return protectionType;
    }

    /**
     * Sets the value of the protectionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtectionType(String value) {
        this.protectionType = value;
    }

    /**
     * Gets the value of the applicationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getApplicationDate() {
        return applicationDate;
    }

    /**
     * Sets the value of the applicationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setApplicationDate(XMLGregorianCalendar value) {
        this.applicationDate = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

}
