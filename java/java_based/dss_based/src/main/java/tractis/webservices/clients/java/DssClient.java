package tractis.webservices.clients.java;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xmlbeans.XmlException;
import org.w3.x2000.x09.xmldsig.SignatureDocument;

import tractis.webservices.clients.java.http.HttpSender;
import tractis.webservices.clients.java.protocol.DSSResult;
import tractis.webservices.clients.java.utils.StreamUtils;
import tractis.webservices.clients.java.xml.AnyTypeHelper;
import x0CoreSchema.oasisNamesTcDss1.AnyType;
import x0CoreSchema.oasisNamesTcDss1.DocumentType;
import x0CoreSchema.oasisNamesTcDss1.ReturnUpdatedSignatureDocument;
import x0CoreSchema.oasisNamesTcDss1.VerifyRequestDocument;
import x0CoreSchema.oasisNamesTcDss1.VerifyResponseDocument;
import x0CoreSchema.oasisNamesTcDss1.Base64DataDocument.Base64Data;
import x0CoreSchema.oasisNamesTcDss1.InputDocumentsDocument.InputDocuments;
import x0CoreSchema.oasisNamesTcDss1.ReturnUpdatedSignatureDocument.ReturnUpdatedSignature;
import x0CoreSchema.oasisNamesTcDss1.SignatureObjectDocument.SignatureObject;
import x0CoreSchema.oasisNamesTcDss1.VerifyRequestDocument.VerifyRequest;

/**
 * Main class for DSS invocations, allows validation and update processes over XAdES 1.3.2 signatures
 *
 */
public class DssClient 
{
	
	private static String serverURL = "https://backend.tractis.com/is2/dss/verify";
	
	/**
	 * Validates and updates a signature to a given form
	 * @param signature The inputStream containing a XAdES 1.3.2 signature 
	 * @param document The document signed
	 * @param form The form to which the signature will be update to , It must be in the urn:oasis:names:tc:dss:1.0:profiles:AdES:forms:ES-* form
	 * @return The result of the validation plus the signature updated to the form if the provided signature was valid
	 * @throws Exception
	 */
	public DSSResult validateAndUpdateSignature(InputStream signature, InputStream document, String form) throws Exception{
		VerifyRequestDocument request = composeRequest(signature,document, form);
		InputStream serverResponse = this.sendXmlObject(request.newInputStream());
		return this.processResponse(serverResponse);
	}
	
	/**
	 * Validates a signature
	 * @param signature The inputStream containing a XAdES 1.3.2 signature 
	 * @param document The document signed
	 * @return The result of the validation
	 * @throws Exception
	 */
	public DSSResult validateSignature(InputStream signature, InputStream document) throws Exception{
		VerifyRequestDocument request = composeRequest(signature,document, null);
		InputStream serverResponse = this.sendXmlObject(request.newInputStream());
		return this.processResponse(serverResponse);
	}
	
	private VerifyRequestDocument composeRequest(InputStream signature, InputStream document, String form) throws XmlException, IOException{
		SignatureDocument signatureDocumentObject = SignatureDocument.Factory.parse(signature);
		
		//Create the message structure
		VerifyRequestDocument doc = VerifyRequestDocument.Factory.newInstance();
		VerifyRequest verifyRequest = doc.addNewVerifyRequest();
		InputDocuments inputDocuments = verifyRequest.addNewInputDocuments();
		SignatureObject signatureObject = verifyRequest.addNewSignatureObject();
		
		//Setting the document
		DocumentType newDocument = inputDocuments.addNewDocument();
		Base64Data base64Data = newDocument.addNewBase64Data();
		base64Data.setByteArrayValue(StreamUtils.getBytesFromStream(document));
		
		//Setting the signature
		signatureObject.setSignature(signatureDocumentObject.getSignature());
		
		//If update to form is requested it will be included as optional input
		if (form != null){
			AnyType type = verifyRequest.addNewOptionalInputs();
			ReturnUpdatedSignatureDocument rusDoc = ReturnUpdatedSignatureDocument.Factory.newInstance();
			ReturnUpdatedSignature rus = rusDoc.addNewReturnUpdatedSignature();
			rus.setType(form);
			AnyTypeHelper.appendAsLastChild(type, rus);
			
			//Profile must be set
			verifyRequest.setProfile("urn:oasis:names:tc:dss:1.0:profiles:XAdES");
		}
		
		return doc;
	}
	
	private InputStream sendXmlObject(InputStream xml) throws Exception {
		HttpSender sender = new HttpSender(serverURL);
		return sender.dssSend(xml,HttpSender.applicationMimeType);
	}
	
	private DSSResult processResponse(InputStream serverResponse) throws XmlException, IOException, ParserConfigurationException{
		VerifyResponseDocument responseDoc=VerifyResponseDocument.Factory.parse(serverResponse);
		return new DSSResult(responseDoc);
	}
	
}
