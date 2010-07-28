package tractis.webservices.clients.java.auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * You must provide the credentials that will be used as service auth on a credentials.properties file.
 * To get them just create a business account on Tractis and a new API Key on webservices
 * Use API Identifier as user and Secret as pass, and provided it as "user" and "pass" on the file  
 * @author dave
 *
 */
public class Credentials {
	
	public static String user,pass;
	
	static {
		InputStream credentials = ClassLoader.getSystemResourceAsStream("credentials.properties");
		if (credentials == null){
			throw new RuntimeException("credentials.properties file must be provided ");
		}
		
		Properties properties = new Properties(); 
		try { 
			properties.load(credentials);
			user = (String) properties.get("user");
			pass = (String) properties.get("pass");
			
			System.out.println("Initializing DSS Client with username " + user);
		} 
		catch (IOException e) {
			throw new RuntimeException("Cannot load credentials.properties file");
		} 
		
	}
	
}
