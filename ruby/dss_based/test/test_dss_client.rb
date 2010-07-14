require 'test/unit'
require 'dss_client'

class TestDssClient < Test::Unit::TestCase

	def test_validate_simple_signature_no_update
		result = @connector.validate_signature(signature, document, {})
		assert(result.valid?)
		assert(result.updated_signatures.empty?)

		#Alteration is detected
		result = @connector.validate_signature(signature, document + "padding", {})
		assert(!result.valid?)
		assert(result.invalid?)
	end

	def test_validate_with_update_to_es_x_l
		result = @connector.validate_signature(signature, document, {:form => 'urn:oasis:names:tc:dss:1.0:profiles:AdES:forms:ES-X-L'})
		assert(result.valid?)
		assert(!result.updated_signatures.empty?)

		#Revalidation works too
		updated_signature = result.updated_signatures['urn:oasis:names:tc:dss:1.0:profiles:AdES:forms:ES-X-L'].to_s
		result = @connector.validate_signature(updated_signature, document, {:form => 'urn:oasis:names:tc:dss:1.0:profiles:AdES:forms:ES-X-L'})
		assert(result.valid?)
	end

	def test_timestamp
		result = @connector.stamp_content(document)
		timestamp = result.signature_object

		#Validate generated timestamp
		result = @connector.validate_signature(timestamp.to_s, document, {})
		assert(result.valid?)
		assert(result.updated_signatures.empty?)

		#Alteration is detected
		result = @connector.validate_signature(timestamp.to_s, document + "padding", {})
		assert(!result.valid?)
		assert(result.invalid?)
	end


	def setup
		# Points to tractis env, if you want to test it point to backend.tractis.com and fill credentials 
		# but this will generate real requests so beware of costs
		@connector = DssClient.new()
	end


private
	def signature
		fixture("signature.xml")
	end

	def document
		fixture("document.xml")
	end

	def fixture(name)
		File.read(File.dirname(__FILE__) + "/fixtures/"  + name)
	end

end
