package tractis.webservices.clients.java.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
	
	   /**
	    * From: org.apache.xml.security.utils.JavaUtils
	    * This method reads all bytes from the given InputStream till EOF and returns
	    * them as a byte array.
	    *
	    * @param inputStream
	    * @return
	    *
	    * @throws IOException
	    */
	   public static byte[] getBytesFromStream(InputStream inputStream) throws IOException {

	      byte refBytes[] = null;

	      ByteArrayOutputStream baos = null;
	      
	      try {
	         baos = new ByteArrayOutputStream();
	         byte buf[] = new byte[1024];
	         int len;

	         while ((len = inputStream.read(buf)) > 0) {
	            baos.write(buf, 0, len);
	         }

	         refBytes = baos.toByteArray();
	      } finally {
		    	 if ( baos != null )
		    		 baos.close();
	      }

	      return refBytes;
	   }

	   /**
	    * From: org.apache.xml.security.utils.JavaUtils
	    * Method getBytesFromFile
	    *
	    * @param fileName
	    * @return
	    *
	    * @throws FileNotFoundException
	    * @throws IOException
	    */
	   public static byte[] getBytesFromFile(String fileName)
	           throws FileNotFoundException, IOException {

	      byte refBytes[] = null;
	      FileInputStream fisRef = null;
	      ByteArrayOutputStream baos = null;
	      
	      try {
	         fisRef = new FileInputStream(fileName);
	         baos = new ByteArrayOutputStream();
	         byte buf[] = new byte[1024];
	         int len;

	         while ((len = fisRef.read(buf)) > 0) {
	            baos.write(buf, 0, len);
	         }

	         refBytes = baos.toByteArray();
	      } finally {
		    	 if ( fisRef != null )
		    		 fisRef.close();
		    	 if ( baos != null )
		    		 baos.close();
	      }

	      return refBytes;
	   }

}
