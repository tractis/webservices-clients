package tractis.webservices.clients.java.http;

import java.io.InputStream;
import java.net.URI;



/**
 * This class dispatches HTTP messages to DSS servers.
 * @author Dave Garcia
 */
public class HttpSender {
	
	private HTTPCommunicationManager manager;
	private String requestURI;
	private Integer timeout = 50000;
	
	public static final String applicationMimeType= "text/xml; charset=\"UTF-8\"";
	public static final String timestampMimeType= "application/timestamp-query";
	
	
	public HttpSender(String requestUri) throws Exception {
			this.manager=new HTTPCommunicationManager();
			this.requestURI=requestUri;
	}

	public InputStream dssSend(InputStream request,String contentType) throws Exception {
		
		InputStream response=this.send(request,contentType);
		return response;
	}
	
	public InputStream send(InputStream request,String contentType) throws Exception{
		return this.manager.post(URI.create(requestURI),request,contentType,timeout,timeout);
	}

	public Integer getTimeout() {
		return timeout;
	}

	/**
	 * Determines the socket timeout
	 * @param timeout The timeout in millis, default value is 5000
	 */
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
}