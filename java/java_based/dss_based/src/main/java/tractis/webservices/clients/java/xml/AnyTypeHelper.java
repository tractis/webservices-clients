package tractis.webservices.clients.java.xml;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlCursor.TokenType;
import org.apache.xmlbeans.impl.common.QNameHelper;



/**
 * Provides functions to get and set attributes from a ds:any object.
 * @author Dave Garcia
 *
 */
public class AnyTypeHelper {
	

	/**
	 * Appends the content of an xml object to the end of the given one.
	 * 
	 * @param xmlAny The given object to which to attach.
	 * @param xmlAttachedObject The object to be attached.
	 */
	public static XmlObject appendAsLastChild(XmlObject xmlObject, XmlObject xmlAttachObject)
	{
		
		//cursor over the given object 
		XmlCursor cursor = xmlObject.newCursor();
		//cursor over the object to attach
		XmlCursor attachCursor = xmlAttachObject.newCursor();

		//Uncoment for debugging purposes
		//		cursor.dump();
		//		attachCursor.dump();
		
		try{
			if (!cursor.isStart() && (cursor.isStartdoc() && cursor.toFirstChild())){
				throw new InvalidParameterException("Cannot obtain the first child of " +
				"a non element");			
				
			}else{
				//we move the cursor over the given object to the end token
				cursor.toEndToken();
					//we must copy all tokens found, normally only one element can be found, but MANY
					//processing instructions could be provided and they MUST be included on the paste process too
					if (attachCursor.isStartdoc()){
						copyAllDoc(cursor,attachCursor);
						cursor.toParent();
						cursor.toLastChild();
					}else{
						//we move the contents from the object to attach, to the given object
						attachCursor.copyXml(cursor);
						//After copying we need to go back to the start of the pasted element
						cursor.toPrevSibling();						
					}
				return cursor.getObject();
			}
		} finally{
			//deallocate resources
			cursor.dispose();
			attachCursor.dispose();
		}
	}
	
	/**
	 * Copy all the document content to the destination
	 * @param destination
	 * @param origin
	 */
	private static void copyAllDoc(XmlCursor destination,XmlCursor origin){
		
		
		origin.toStartDoc();
		origin.toNextToken();
		
		destination.push();
		
		TokenType currentTokenType;
		//Go after all root tokens on the current document
		while ((currentTokenType=origin.currentTokenType())!=TokenType.ENDDOC){
			//Element token with start and end 
			if (currentTokenType.equals(TokenType.START)){
				origin.copyXml(destination);
				origin.toEndToken();
				origin.toNextToken();
			}else{
					//Atomic token
					origin.copyXml(destination);
					origin.toNextToken();
			}
			
		}
		
		destination.pop();
		
		//We are in the end of an element that's that the appended child is the only one
		if (destination.currentTokenType().equals(TokenType.END)){
			destination.toFirstChild();
		}
		
	}
	
	/**
	 * Retrieves the children from a given xmlObject
	 * @param xmlObject
	 * @return
	 */
	public static XmlObject[] getChildren(XmlObject xmlObject){
		if (xmlObject==null) return null;
		else{
			XmlCursor currentObjectCursor=xmlObject.newCursor();
			currentObjectCursor.toFirstChild();
			List<XmlObject> child=new ArrayList<XmlObject>();
			//go after every child
			do{
				
				XmlObject currentChild=currentObjectCursor.getObject();
				if (currentChild!=null) child.add(currentChild);
				
			}while(currentObjectCursor.toNextSibling());
			
			Object []result=child.toArray();
			XmlObject castedResult[]=new XmlObject[result.length];
			
			for (int i = 0; i < result.length; i++) {
				castedResult[i]=(XmlObject) result[i];
			}
			return castedResult;
		}
	}
	
	
	/**
	 * Detach the current element from their parents, we return an element type
	 * @param toBeDetached
	 * @return
	 * @throws XmlException
	 * @throws IOException
	 * @throws ParserConfigurationException 
	 */
	public static XmlObject detach(XmlObject toBeDetached) throws XmlException, IOException, ParserConfigurationException{
		XmlObject detachedObject;
		detachedObject=detachCursor(toBeDetached,null);
		return detachedObject;
	}
	
	/**
	 * Detach the current element from their parents, we return an element type
	 * @param toBeDetached
	 * @return
	 * @throws XmlException
	 * @throws IOException
	 * @throws ParserConfigurationException 
	 */
	public static XmlObject detach(XmlObject toBeDetached,SchemaType type) throws XmlException, IOException, ParserConfigurationException{
		XmlObject detachedObject;
		detachedObject=detachCursor(toBeDetached,type);
		return detachedObject;
	}
	
	
	
	private static XmlObject detachCursor(XmlObject toBeDetached,SchemaType type) throws XmlException, IOException, ParserConfigurationException{
		SchemaType docType;
		if (type==null){
			docType =getDocumentType(toBeDetached);
		}else{
			docType=type;
		}
		
		XmlObject destinationObject=buildEmptyDocumentObject();
		
		XmlCursor destination=destinationObject.newCursor();
		destination.toNextToken();
		//destination.dump();
		
		XmlCursor tobeAttachedCursor=toBeDetached.newCursor();
		tobeAttachedCursor.copyXml(destination);
		//destination.dump();
		destination.toStartDoc();
		
		//destination.toFirstContentToken();
		XmlObject result=destination.getObject();
		
		//The object has a declared document type
		if (docType!=null){
			result=result.changeType(docType);
			destination.toFirstContentToken();
			return destination.getObject(); 
		}else{
			return result;
		}	
	}	
	
	static DocumentBuilder builder = init();

	private static DocumentBuilder init() {
		try {
			return DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			return null;
		}
	}
	
	/**
	 * Builds an empty xmlObject representing a document
	 * 
	 * @return
	 * @throws XmlException
	 */
	public static XmlObject buildEmptyDocumentObject() throws XmlException {
		return XmlObject.Factory.parse(builder.newDocument());
	}
	
	public static SchemaType getDocumentType(XmlObject element) {
		if (element == null) {
			return null;
		} else {
			XmlCursor cursor = element.newCursor();
			QName name = cursor.getName();
			SchemaTypeSystem system = element.schemaType().getTypeSystem();
			SchemaType result = system.findDocumentType(name);
			return result;
		}
	}
	/**
	 * Flushes the given object recalculating its internal namespaces
	 * To do it, it recovers all declared namespaces and prefixes
	 * Inverts the map because we need it in the inverse way
	 * Prefixes all the elements in the given namespace with the resolved prefix
	 * @param subject
	 */
	public static void flush(XmlObject subject){
		
		Map<String,String> namespaces=new HashMap<String,String>();
		XmlCursor cursor=subject.newCursor();
		
		recalculatePrefixes(cursor,namespaces);
		
		cursor.dispose();
	}
	
	private static Map<String,String> getCursorNS(XmlCursor cursor){
		Map<String,String> tmp=new HashMap<String,String>();
		cursor.getAllNamespaces(tmp);
		tmp=invertMap(tmp);
		
		return tmp;
	}
	
	private static void recalculatePrefixes(XmlCursor cursor,Map<String,String> psisNS){
		
		int remainingTokens=0;
		cursor.push();
		TokenType currentTokenType=cursor.currentTokenType();
		TokenType previousTokenType=null;
		
		Stack<Map<String,String>> namespacesStack=new Stack<Map<String,String>>();
		Map<String,String> namespaces=new HashMap<String,String>();
		/**
		 * Go througth every token from to current start to their equivalent ending token 
		 */
		while(!(previousTokenType==TokenType.END && remainingTokens==0)){
			
			/*
			 * Start token processing
			 * 1.- Changes node prefix if needed
			 * 2.- If this node defines a new namespace we check that it's defined as an attribute of the current node,
			 * 		otherwise we add it
			 */
			if (currentTokenType.isStart()){
				//Stack currentNamespace declaration
				namespacesStack.push(namespaces);
				//Update all namespaces
				namespaces=getCursorNS(cursor);
				
				QName elementName=cursor.getName();

				if (!(elementName.getNamespaceURI() == null || elementName.getNamespaceURI().equals(""))){
					String prefix=elementName.getPrefix();
					
					if (prefix==null || prefix.equals("")){
						cursor.setName(recalculatePrefix(cursor,elementName,namespaces,psisNS));				
					}					
				}
				remainingTokens++;
			}
						
			/*
			 * End node processing, only remaining opened nodes var will be refreshed
			 */
			if (currentTokenType.isEnd()) {
				remainingTokens--;
				//Recover the namespaces declared previously to the current start declaration
				namespaces=(Map<String, String>) namespacesStack.pop();
			}
			
			///Go to the next token
			previousTokenType=currentTokenType;
			currentTokenType=cursor.toNextToken();
		}
		cursor.pop();
	}
	
	/**
	 * Add the namespace with the given prefix and uri to the current element
	 * @param cursor
	 * @param prefix
	 */
	private static void addNamespace(XmlCursor cursor,String prefix){
			cursor.push();
				String ns;
				ns=cursor.getName().getNamespaceURI();			
				boolean hasFirstAttribute=cursor.toFirstAttribute();
				if (!hasFirstAttribute) {
					boolean hasFirstChild=cursor.toFirstChild();
					if (!hasFirstChild){
						cursor.toEndToken();
					}
				}
				
				cursor.insertNamespace(prefix,ns);
				
			cursor.pop();
	}
	
	private static QName recalculatePrefix(XmlCursor cursor,QName elementName,Map<String,String> namespaces, Map<String, String> psisNS){
		String prefix;
		if (!namespaces.containsKey(elementName.getNamespaceURI())){
			//Check for psis defined prefixes
			
			//If psis define a prefix for this namespace it will be used as the default one
			if (psisNS.containsKey(elementName.getNamespaceURI())){
				prefix=psisNS.get(elementName.getNamespaceURI());
			}
			//Otherwise the prefic will be generated
			else{
				prefix=QNameHelper.suggestPrefix(elementName.getNamespaceURI());
			}
			
			//Adds the namespace attribute to the current element 
			addNamespace(cursor,prefix);
		}
		//Retrieve document defined mappings
		else{
			prefix=namespaces.get(elementName.getNamespaceURI());
		}
		
		return new QName(elementName.getNamespaceURI(),elementName.getLocalPart(),prefix);
	}
	
	private static Map<String,String> invertMap(Map<String,String> par){
		Map<String,String> result=new HashMap<String,String>();
		for(Entry<String,String> currentEntry:par.entrySet()){
			result.put(currentEntry.getValue(),currentEntry.getKey());
		}
		return result;
	}
}
