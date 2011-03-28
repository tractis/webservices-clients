#!/usr/bin/env ruby
require 'rubygems'
require 'httpclient'
gem 'soap4r'
require 'lib/tractisDriver.rb'
require 'base64'


class ContentStorageServiceClient
    
  def self.location=(value)
    @@location = value
  end

  #Creates the endpoint client
  def initialize
    @@location ||= '/content_storage/lta'
    endpoint_url = "https://api.tractis.com:443/lta".to_s #TODO change it to point to localhost!
    puts "Endpoint url is #{endpoint_url}"
    @endpoint = SOAPport.new(endpoint_url)
    h = @endpoint.streamhandler.client
    basic_auth = h.www_auth.basic_auth
    basic_auth.challenge(URI.parse(endpoint_url), nil)

    # run ruby with -d to see SOAP wiredumps.
    @endpoint.wiredump_dev = STDERR if $DEBUG
  end

  def endpoint
   @endpoint
  end

  def store(content, properties)
    props = marshall_properties(properties)

    #Note that the content is base64 converted before the store is made to protect it from being modified
    request = ContentStoreRequest.new(SOAP::SOAPBase64.new(content), props)
    ss = Time.new
    response = @endpoint.store(request)
    se = Time.new - ss
    puts "Store elapsed #{se}"

    identifier = response.contentIdentifier
    ensure_stored(content, properties, identifier)
    identifier
  end

  #Transforms a hash into the expected form of properties
  def marshall_properties(properties)
    if !properties.nil?
      props = Properties.new
      properties.keys.each do |current_prop_key|
        property = Property.new
        property.xmlattr_name= current_prop_key
        property.xmlattr_value= properties[current_prop_key]
        props << property
      end
    else
      props = nil
    end

    return props
  end



  #checks that the content has been stored correctly
  def ensure_stored(content, properties, content_identifier)
    recovered_content = recover(content_identifier)
    raise "Content not present" if (recovered_content.nil?)	 

    protected_content = recover_content(recovered_content)
    raise "Content not protected" unless (protected_content.protectionMeasure.length > 0)

    decoded_content = decode_content(protected_content) #The content is decoded from the base64 encoded form returned by the server
    raise "Content missmatch" if (content != decoded_content)

    #Check properties if defined
    if !properties.nil? 
      unmarshalled_properties = protected_content.properties.nil? ? nil : unmarshall_properties(protected_content.properties)
      raise "Properties returned does not match " unless unmarshalled_properties == properties
    end
  end


  #Transforms from the form of properties returned by the server to the client hash implementation
  def unmarshall_properties(properties)
    unmarshalled_properties = Hash.new
    properties.each do |prop|
      unmarshalled_properties[prop.xmlattr_name] = prop.xmlattr_value		 
    end

    return unmarshalled_properties
  end

  #Decodes the base64 encoded content returned by the server
  def decode_content(protected_content)
    return Base64.decode64(Base64.decode64(protected_content.content))
  end

  #Recovers the given content
  def recover(content_identifier)
    request = ContentRecoverRequest.new(content_identifier, nil)
    rs = Time.new
    recovered_content = @endpoint.recover(request)
    re = Time.new - rs
    puts "Recover elapsed #{re}"
    return recovered_content
  end

  def recover_by_properties(properties)
    request = ContentRecoverRequest.new(nil,marshall_properties(properties))
    recovered_content = @endpoint.recover(request)
    return recovered_content
  end

  #Recovers the content from a recover response
  def recover_content(recovered_content)
    if (recovered_content.protectedContent.nil? || recovered_content.protectedContent.length == 0)
      raise "No content recovered"
    elsif (recovered_content.protectedContent.length == 1)
      return recovered_content.protectedContent[0]
    else
      result = Array.new
      recovered_content.protectedContent.each {|current| result << current}
      return result
    end
  end

  #Deletes the given content
  def delete(identifier)
	request = ContentRemoveRequest.new(identifier)
	@endpoint.remove(request)
  end
end
