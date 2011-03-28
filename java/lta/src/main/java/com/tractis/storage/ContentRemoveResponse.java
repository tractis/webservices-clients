
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
 *       &lt;choice>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element ref="{http://storage.tractis.com/}ProtectedContent"/>
 *         &lt;/sequence>
 *         &lt;element name="ServerMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "protectedContent",
    "serverMessage"
})
@XmlRootElement(name = "ContentRemoveResponse")
public class ContentRemoveResponse {

    @XmlElement(name = "ProtectedContent", required = true, type = ProtectedContent.class)
    protected List<ProtectedContent> protectedContent;
    @XmlElement(name = "ServerMessage")
    protected String serverMessage;

    /**
     * Gets the value of the protectedContent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the protectedContent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtectedContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProtectedContent }
     * 
     * 
     */
    public List<ProtectedContent> getProtectedContent() {
        if (protectedContent == null) {
            protectedContent = new ArrayList<ProtectedContent>();
        }
        return this.protectedContent;
    }

    /**
     * Gets the value of the serverMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerMessage() {
        return serverMessage;
    }

    /**
     * Sets the value of the serverMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerMessage(String value) {
        this.serverMessage = value;
    }

}
