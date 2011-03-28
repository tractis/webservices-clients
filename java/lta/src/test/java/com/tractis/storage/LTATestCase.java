package com.tractis.storage;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.tractis.ws.client.stubs.ContentStorageStub;

/**
 * A simple test case illustrating all LTA available features 
 * @author dave
 *
 */
public class LTATestCase extends TestCase {
	
	String user ="MY_USER" , pass = "MY_PASS" ;
	ContentStorageStub stub = null;
	
	public void setUp(){
		try {
			if (user == null || pass == null) throw new RuntimeException("User and pass not set, get them at https://www.tractis.com/webservices/lta and populate variables in order to run tests");
			stub = new ContentStorageStub(user, pass);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void testStore(){
		Map<String,String> meta = new HashMap<String, String>();
		meta.put("Tag", "My content");
		meta.put("Time", new Date().toGMTString()); //Used to given an unique timestamp property
		
		ContentStoreResponse storedContent = this.stub.storeContent("Content".getBytes(), meta);
		
		//Ensure item can be recovered
		String contentIdentifier = storedContent.contentIdentifier;
		System.out.println("Storing content, id is " + contentIdentifier);
		
		ProtectedContent recoverContent = this.stub.recoverContent(contentIdentifier);
		
		//Check content matches
		assertTrue("Content Matches",Arrays.equals("Content".getBytes(), recoverContent.getContent()));
		
		//Assert content is protected
		assertEquals(1, recoverContent.getProtectionMeasure().size());
		
		//Recover by properties
		Collection recoverContentCollection = this.stub.recoverContent(meta);
		ProtectedContent recoveredResponseByProperties = (ProtectedContent) recoverContentCollection.iterator().next();
		String recoveredResponseByPropertiesIdentifier = recoveredResponseByProperties.contentIdentifier;
		
		//Check content identifier matches
		assertEquals(contentIdentifier, recoveredResponseByPropertiesIdentifier);
		
		//Remove element
		ProtectedContent removeElement = this.stub.removeElement(contentIdentifier);
		assertEquals("Removed element matches", contentIdentifier, removeElement.contentIdentifier);
		
		//Check element was removed
		assertNull("Element has been removed",this.stub.recoverContent(contentIdentifier));
	}
	
}
