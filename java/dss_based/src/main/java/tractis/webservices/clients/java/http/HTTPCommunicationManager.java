package tractis.webservices.clients.java.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

/**
 * Deals with http communication, currently it only supports post verb over http and https
 * @author dave
 *
 */
public class HTTPCommunicationManager {

	enum ComunicationType{
		HTTP,HTTPS
	}

	/**
	 * Performs a HTTP Post request
	 * @param uri
	 * @param input
	 * @param mimeType
	 * @param timeOut
	 * @param communicationTimeOut
	 * @return
	 * @throws Exception
	 */
	public InputStream post(URI uri,InputStream input, String mimeType,Integer timeOut,Integer communicationTimeOut) throws Exception {
		HttpClientParams params = new HttpClientParams();
	
		if (communicationTimeOut != null) {
			params.setSoTimeout(communicationTimeOut);
		}
		
		HttpClient client = new HttpClient(params);

		//Setting credentials
		client.getState().setCredentials(
				new AuthScope(uri.getHost(), uri.getPort()), 
				new UsernamePasswordCredentials(tractis.webservices.clients.java.auth.Credentials.user, tractis.webservices.clients.java.auth.Credentials.pass) );

		client.getParams().setAuthenticationPreemptive(true);

		HostConfiguration hostConfiguration = new HostConfiguration();
		hostConfiguration.setHost(uri.toString());

		PostMethod method = new PostMethod(uri.toString());
		method.setDoAuthentication(true);
		method.setRequestEntity(new InputStreamRequestEntity(input, mimeType));
		method.setRequestHeader("Content-Id","dss-request");
		
		try {
			int resultCode = client.executeMethod(hostConfiguration, method);
			if (resultCode != HttpStatus.SC_OK) {
				throw new Exception("HTTP/S method failed: " + method.getStatusLine() + " " + resultCode);
			}
			return method.getResponseBodyAsStream();
		} catch (HttpException e) {
			throw new Exception("HTTP/S error while opening connection." + method.getStatusCode());
		} catch (IOException e) {
			throw new Exception("IO Exception Opening Communication", e);
		}
	}

}
