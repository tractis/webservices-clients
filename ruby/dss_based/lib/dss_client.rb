require File.dirname(__FILE__)+'/va_connector'
require File.dirname(__FILE__)+'/dss_response'
require 'rexml/document'
require 'base64'

class DssClient
	
	require 'openssl'

	def initialize(url = "https://api.tractis.com/sva")
		@va_connector = VaConnector.new(url)
	end

	def validate_signature(signature, document, options)

		if document.nil?
			request = simple_request_no_doc
			request = compose_signature(request, signature)
		else		
			if (options.keys.include? :form)
				request = update_request(options[:form])
			else
				request = simple_request
			end
			request = compose_request(request, signature, document)
		end
		response = @va_connector.send_request(request)
		return DssResponse.new(response)
	end

	def stamp_content(toBeStamped)
		request = stamp_request(toBeStamped)
		response = @va_connector.send_request(request)
		return DssResponse.new(response)
	end

	private
	
	def compose_request(request, signature, document)
		#we need to substitute the document 
		digest_value_element = REXML::XPath.first request, "//dss:InputDocuments/dss:DocumentHash/ds:DigestValue"
		digest_value_element.text = digest_content(document)

		#Now we must attach the signature itself WARN! check this on a es-c not a es-x 
		signature_object_element = REXML::XPath.first request, "//dss:SignatureObject"		
		signature_element = REXML::Document.new(signature)
		signature_object_element.replace_child(signature_object_element.children[0], signature_element)
		return request
	end

	def compose_signature(request,signature)
		#Now we must attach the signature itself WARN! check this on a es-c not a es-x 
		signature_object_element = REXML::XPath.first request, "//dss:Base64XML"		
		require 'base64'
		signature_element = Base64.encode64(signature)
		#require 'ruby-debug';debugger
		signature_object_element.text = signature_element

		return request
	end

	def digest_content(document)
		return Base64.encode64(OpenSSL::Digest::SHA1.digest(document))
	end

	#A simple validation request
	def simple_request
 xml = <<-XML
<?xml version="1.0" encoding="UTF-8"?>
	<dss:VerifyRequest xmlns:dss="urn:oasis:names:tc:dss:1.0:core:schema" Profile="urn:oasis:names:tc:dss:1.0:profiles:XAdES">
	    <dss:InputDocuments>
		 <dss:DocumentHash ID="Doc1">
                    <ds:DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha1" xmlns:ds="http://www.w3.org/2000/09/xmldsig#"/>
                    <ds:DigestValue xmlns:ds="http://www.w3.org/2000/09/xmldsig#">To be replaced</ds:DigestValue>
         </dss:DocumentHash>
            </dss:InputDocuments>
	<dss:SignatureObject><ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#" Id="Signature">To be replaced</ds:Signature></dss:SignatureObject>
</dss:VerifyRequest>
XML
		return REXML::Document.new(xml)

	end

	def simple_request_no_doc_enveloping
xml = <<-XML
<?xml version="1.0" encoding="UTF-8"?>
	<dss:VerifyRequest xmlns:dss="urn:oasis:names:tc:dss:1.0:core:schema" Profile="urn:oasis:names:tc:dss:1.0:profiles:XAdES">
	   <dss:OptionalInputs>
		        <dss:ReturnUpdatedSignature Type='urn:oasis:names:tc:dss:1.0:profiles:AdES:forms:ES-C'/>
	    </dss:OptionalInputs>
	<dss:SignatureObject><ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#" Id="Signature">To be replaced</ds:Signature></dss:SignatureObject>
</dss:VerifyRequest>
	XML
		return REXML::Document.new(xml)
	end

	def simple_request_no_doc
xml = <<-XML
<?xml version="1.0" encoding="UTF-8"?>
	<dss:VerifyRequest xmlns:dss="urn:oasis:names:tc:dss:1.0:core:schema" Profile="urn:oasis:names:tc:dss:1.0:profiles:XAdES">
	   <dss:OptionalInputs>
		        <dss:ReturnUpdatedSignature Type='urn:oasis:names:tc:dss:1.0:profiles:AdES:forms:ES-C'/>
	    </dss:OptionalInputs>
	    <dss:InputDocuments><dss:Document><dss:Base64XML>CONTENT</dss:Base64XML></dss:Document></dss:InputDocuments>
</dss:VerifyRequest>
XML

		return REXML::Document.new(xml)

	end

	def update_request(form)
xml = <<-XML
	<?xml version="1.0" encoding="UTF-8"?>
	<dss:VerifyRequest xmlns:dss="urn:oasis:names:tc:dss:1.0:core:schema" Profile="urn:oasis:names:tc:dss:1.0:profiles:XAdES">
	    <dss:OptionalInputs>
		        <dss:ReturnUpdatedSignature/>
	    </dss:OptionalInputs>	    
		<dss:InputDocuments>
		 <dss:DocumentHash ID="Doc1">
                    <ds:DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha1" xmlns:ds="http://www.w3.org/2000/09/xmldsig#"/>
                    <ds:DigestValue xmlns:ds="http://www.w3.org/2000/09/xmldsig#">To be replaced</ds:DigestValue>
         </dss:DocumentHash>
            </dss:InputDocuments>
	<dss:SignatureObject><ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#" Id="Signature">To be replaced</ds:Signature></dss:SignatureObject>
</dss:VerifyRequest>
	XML
		request = REXML::Document.new(xml)
		#If default is requested no specific form will be triggered
		if (form != :default)
			node = REXML::XPath.first request, "//dss:ReturnUpdatedSignature", namespaces
			node.attributes['Type'] = form

		end
		return request
	end

	def stamp_request(content)
xml = <<-XML
<SignRequest xmlns="urn:oasis:names:tc:dss:1.0:core:schema">
  <OptionalInputs>
	<ReturnSigningTimeInfo/>
    <SignatureType>urn:oasis:names:tc:dss:1.0:core:schema:XMLTimeStampToken</SignatureType>
  </OptionalInputs>
  <InputDocuments>
    <Document>
      <Base64Data></Base64Data>
    </Document>
  </InputDocuments>
</SignRequest>
XML
		request = REXML::Document.new(xml)
		digest_value_element = REXML::XPath.first request, "//dss:InputDocuments/dss:Document/dss:Base64Data", namespaces
		digest_value_element.text = Base64.encode64(content)
		
		request
	end

	
	def namespaces
		{"dss" => "urn:oasis:names:tc:dss:1.0:core:schema", "ds" => "http://www.w3.org/2000/09/xmldsig#"}
	end
	
end
