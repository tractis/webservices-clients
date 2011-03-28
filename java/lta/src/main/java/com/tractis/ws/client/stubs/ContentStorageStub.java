package com.tractis.ws.client.stubs;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.transport.Channel;

import com.tractis.storage.ContentRecoverRequest;
import com.tractis.storage.ContentRecoverResponse;
import com.tractis.storage.ContentRemoveRequest;
import com.tractis.storage.ContentRemoveResponse;
import com.tractis.storage.ContentStoreRequest;
import com.tractis.storage.ContentStoreResponse;
import com.tractis.storage.ObjectFactory;
import com.tractis.storage.Properties;
import com.tractis.storage.Property;
import com.tractis.storage.ProtectedContent;
import com.tractis.storage.SOAPport;
import com.tractis.storage.contentStorageServiceService;

/**
 * Allows basic operations like storage and recovery from a remote
 * secure storage service
 * @author dave
 *
 */
public class ContentStorageStub {
	
	private ObjectFactory objectFactory;
	private SOAPport contentStoragePortSoap;
	
	public ContentStorageStub(String username, String pass) throws MalformedURLException{
		this.objectFactory = new ObjectFactory();
		contentStorageServiceService client;
		try {
			client = new contentStorageServiceService();
			contentStoragePortSoap = client.getcontentStoragePortSoap();
			
			XFireProxy proxyObject = (XFireProxy) Proxy.getInvocationHandler(contentStoragePortSoap);
			
			Client communicationClient = proxyObject.getClient();
			communicationClient.setUrl("https://api.tractis.com:443/lta");
			
			communicationClient.setProperty(Channel.USERNAME, username);
			communicationClient.setProperty(Channel.PASSWORD, pass);
		} catch (IOException e) {
			throw new RuntimeException("Cannot instantiate client stub",e );
		}
	}
	
	/**
	 * Stores the given content applying the given meta if needed
	 * @param content
	 * @param meta
	 * @return
	 */
	public ContentStoreResponse storeContent(byte[] content, Map<String,String> meta){
		//Send an store request
		ContentStoreRequest request = this.buildStorageRequest(content, meta);
		ContentStoreResponse response = this.sendStorageRequest(request);
		
		return response;
	}
	
	/**
	 * Recovers the content with the given identifier
	 * @param contentIdentifier
	 * @return
	 */
	public ProtectedContent recoverContent(String contentIdentifier){
		ContentRecoverRequest request = new ContentRecoverRequest();
		request.setContentIdentifier(contentIdentifier);
		ContentRecoverResponse response = this.sendRecoverRequest(request);
		
		List<com.tractis.storage.ProtectedContent> protectedContent = response.getProtectedContent();
		if (protectedContent.isEmpty()){
			return null;
		}else if (protectedContent.size() == 1){
			return protectedContent.get(0);
		}else{
			throw new RuntimeException("Server returned more than one content for identifier "+contentIdentifier);
		}
	}
	
	public ProtectedContent removeElement(String contentIdentifier){
		ContentRemoveRequest request = new ContentRemoveRequest();
		request.setContentIdentifier(contentIdentifier);
		
		ContentRemoveResponse sendRemoveRequest = this.sendRemoveRequest(request);
		List<ProtectedContent> protectedContent = sendRemoveRequest.getProtectedContent();
		
		if (protectedContent.isEmpty()){
			return null;
		}else if (protectedContent.size() == 1){
			return protectedContent.get(0);
		}else{
			throw new RuntimeException("Server returned more than one content for identifier "+contentIdentifier);
		}	}
	
	/**
	 * Returns all the contents matching the given criteria
	 * @param criteria
	 * @return
	 */
	public Collection recoverContent(Map<String,String> criteria){
		ContentRecoverRequest request = this.buildRecoverRequest(criteria);
		ContentRecoverResponse response = this.sendRecoverRequest(request);
		
		return response.getProtectedContent();
	}
	
	protected ContentRecoverRequest buildRecoverRequest(Map<String,String> tags){
		ContentRecoverRequest request = new ContentRecoverRequest();
		request.setProperties(this.buildProperties(tags));
		
		return request;
	}
	
	protected ContentStoreRequest buildStorageRequest(byte[] content, Map<String,String> properties){
		ContentStoreRequest request =  new ContentStoreRequest();
		
		request.setContent(content);
		
		//If the properties are provided they're marshalled
		if (properties != null && !properties.isEmpty()){			
			request.setProperties(this.buildProperties(properties));
		}	
		
		return request;	
	}
	
	public Map<String,String> unmarshall(Properties properties){
		if (properties == null) return null;
		else {
			Map<String,String> result = new HashMap<String, String>();
			for(Property currentProperty : properties.getProperty()){
				result.put(currentProperty.getName(), currentProperty.getValue());
			}
			
			return result;
		}
	}
	
	protected Properties buildProperties(Map<String,String> properties){
		//If the properties are provided they're marshalled
		if (properties != null && !properties.isEmpty()){
			Properties marshalledProperties = objectFactory.createProperties();
			
			for(String currentPropertyName : properties.keySet()){
				Property p = new Property();
				marshalledProperties.getProperty().add(p);
				
				p.setName(currentPropertyName);
				p.setValue(properties.get(currentPropertyName));
				
			}
			
			return marshalledProperties;
		}	
		
		return null;
	}
	
	protected ContentStoreResponse sendStorageRequest(ContentStoreRequest request){
		ContentStoreResponse response = this.contentStoragePortSoap.store(request);
		return response;
	}
	
	protected ContentRecoverResponse sendRecoverRequest(ContentRecoverRequest request){
		ContentRecoverResponse response = this.contentStoragePortSoap.recover(request);
		return response;
	}
	
	protected ContentRemoveResponse sendRemoveRequest(ContentRemoveRequest request){
		ContentRemoveResponse remove = this.contentStoragePortSoap.remove(request);
		return remove;
	}
}