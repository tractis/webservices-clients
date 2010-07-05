require 'rexml/document'
class  DssResponse
	attr_reader :major, :minor

	def initialize(element)
		@element = element
		namespaces = {"dss" => "urn:oasis:names:tc:dss:1.0:core:schema", "ds" => "http://www.w3.org/2000/09/xmldsig#"}
		@major = REXML::XPath.first @element, '//dss:ResultMajor', namespaces
    		@minor = REXML::XPath.first @element, '//dss:ResultMinor', namespaces
	end

	def valid?
	        return ( major.text.include?('Success') and minor.text.include?('OnAllDocuments')  )
	end

	def invalid?
		return ( major.text.include?('Success') and minor.text.include?('IncorrectSignature')  )
	end

	def unknown?
		return ( major.text.include?('InsufficientInformation') )
	end

	def updated_signatures
		signatures = {}

		execute_xpath('//dss:UpdatedSignature').each do |updated_signature|
			form = updated_signature.attributes['Type']
			form =:default if form.nil? #signature updated by the default service policy currently an ES-C
			signatures[form] = REXML::Document.new(updated_signature.children[0].children[0].to_s)
		end

		return signatures
	end

	def signature_object
		result = []
		execute_xpath('//dss:SignResponse/dss:SignatureObject').each do |signature_object|
			result << REXML::Document.new(signature_object.children[0].to_s)
		end
		result
	end
	
	def to_s
		"#{major}:#{minor}"
	end

	def dump
		@element.to_s
	end

	private

	def execute_xpath(path)
		namespaces = {"dss" => "urn:oasis:names:tc:dss:1.0:core:schema", "ds" => "http://www.w3.org/2000/09/xmldsig#"}

		result = []

		REXML::XPath.each @element, path, namespaces do |element|
			result << element
		end

		return result
	end
end

