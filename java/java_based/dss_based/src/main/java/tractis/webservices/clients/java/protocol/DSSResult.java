package tractis.webservices.clients.java.protocol;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.w3.x2000.x09.xmldsig.SignatureDocument;
import org.w3.x2000.x09.xmldsig.SignatureType;

import tractis.webservices.clients.java.xml.AnyTypeHelper;
import x0CoreSchema.oasisNamesTcDss1.AnyType;
import x0CoreSchema.oasisNamesTcDss1.ResponseBaseType;
import x0CoreSchema.oasisNamesTcDss1.UpdatedSignatureType;
import x0CoreSchema.oasisNamesTcDss1.VerifyResponseDocument;
import x0CoreSchema.oasisNamesTcDss1.ResultDocument.Result;

/**
 * Represents a response from a DSS server.
 * Includes processed result and updated signature
 * @author dave
 *
 */
public class DSSResult {

	private ResultCode result;
	private SignatureDocument updatedSignature;
	private String dssMajor, dssMinor;
	
	public DSSResult(VerifyResponseDocument responsedocument) throws XmlException, IOException, ParserConfigurationException{
		ResponseBaseType verifyResponse = responsedocument.getVerifyResponse();
		Result resultCode = verifyResponse.getResult();
		this.processResult(resultCode);
		this.updatedSignature = this.processSignature(verifyResponse.getOptionalOutputs());
	}
	
	private void processResult(Result resultCode){
		this.dssMajor = resultCode.getResultMajor();
		this.dssMinor = resultCode.getResultMinor();
		this.result = this.parseDSSCode();
	}
	
	/**
	 * Now dss code parsing is quite trivial not covering some cases like not all documents covered, complete with all cases 
	 * @return
	 */
	private ResultCode parseDSSCode(){
		if (dssMajor.equals("urn:oasis:names:tc:dss:1.0:resultmajor:Success")){
			if (dssMinor.equals("urn:oasis:names:tc:dss:1.0:resultminor:valid:signature:OnAllDocuments")){
				return ResultCode.VALID;
			}else if (dssMinor.equals("urn:oasis:names:tc:dss:1.0:resultminor:invalid:IncorrectSignature")){
				return ResultCode.INVALID;
			}
		}
		return ResultCode.UNKNOWN;
	}

	/**
	 * Extracts the signature if it has been provided as Optional Output detaching it in order to allow it to be properly serialized
	 * @param optionalOutputs
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws XmlException 
	 */
	private SignatureDocument processSignature(AnyType optionalOutputs) throws XmlException, IOException, ParserConfigurationException {
		if (optionalOutputs == null ) return null;
		XmlObject[] children = AnyTypeHelper.getChildren(optionalOutputs);
		SignatureDocument result = null;
		for (XmlObject currentChildren:children){
			if (currentChildren instanceof UpdatedSignatureType){
				if (result == null) {
					UpdatedSignatureType us = (UpdatedSignatureType)currentChildren; 
					SignatureType signature = us.getSignatureObject().getSignature();
					XmlObject detach = AnyTypeHelper.detach(signature);
					SignatureDocument root = SignatureDocument.Factory.newInstance();
					AnyTypeHelper.appendAsLastChild(root, detach);
					result = (SignatureDocument) root;
				}else{
					throw new RuntimeException("Found more then one updated signature on optional outputs");
				}
			}else{
				System.out.println("Optional output returned "+currentChildren);
			}
		}
		return result;
	}

	
	public String getDssMajor() {
		return dssMajor;
	}

	public String getDssMinor() {
		return dssMinor;
	}


	public ResultCode getResult() {
		return result;
	}

	public InputStream getUpdatedSignature() {
		return updatedSignature == null ? null : updatedSignature.newInputStream();
	}
	
	public String toString(){
		return "result:" + this.result + "\ndss_result: ["+this.dssMajor + ":" + this.dssMinor + "]" + "\nupdated_signature:" + (this.updatedSignature == null); 
	}
}
