package tractis.webservices.clients.java;

import java.io.InputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import tractis.webservices.clients.java.protocol.DSSResult;
import tractis.webservices.clients.java.protocol.ResultCode;

/**
 * Tests DssClient vs a service using some fixtures. This test will only works in environments properly configured to invoke a remote DSS service 
 */
public class DssClientTest 
    extends TestCase
{
	
	private DssClient client ;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DssClientTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DssClientTest.class );
    }

    public void setUp(){
    	client = new DssClient();
    }
    
    /**
     * Performs a validation of a valid ES-X-L signature
     * @throws Exception 
     */
    public void testSimpleValidation() throws Exception
    {
    	DSSResult validateSignature = client.validateSignature(signature(), document());
    	assertEquals(ResultCode.VALID, validateSignature.getResult());
    	assertNull(validateSignature.getUpdatedSignature());
    }
    
    /**
     * Performs a signature update process, then recovers updated signature (detached from message) and triggers revalidation
     * @throws Exception
     */
    public void testValidateDetachedSignature() throws Exception{
    	DSSResult validateSignature = client.validateAndUpdateSignature(signature(), document(), "urn:oasis:names:tc:dss:1.0:profiles:AdES:forms:ES-X-L");
    	assertEquals(ResultCode.VALID, validateSignature.getResult());
    	assertNotNull(validateSignature.getUpdatedSignature());
    	
    	//Trigger revalidating signature
    	validateSignature = client.validateSignature(validateSignature.getUpdatedSignature(), document());
    	assertEquals(ResultCode.VALID, validateSignature.getResult());
    	assertNull(validateSignature.getUpdatedSignature());
    }
    
    private InputStream signature(){
    	return resource("signature.xml");
    }
    
    private InputStream document(){
    	return resource("document.txt");
    }
    
    private InputStream resource(String name){
    	return ClassLoader.getSystemResourceAsStream(name);
    }
}
