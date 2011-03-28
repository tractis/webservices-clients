require 'xsd/qname'

# {http://storage.tractis.com/}ContentStoreRequest
#   content - SOAP::SOAPBase64
#   properties - Properties
class ContentStoreRequest
  attr_accessor :content
  attr_accessor :properties

  def initialize(content = nil, properties = nil)
    @content = content
    @properties = properties
  end
end

# {http://storage.tractis.com/}ContentStoreResponse
#   contentIdentifier - SOAP::SOAPString
#   message - SOAP::SOAPString
class ContentStoreResponse
  attr_accessor :contentIdentifier
  attr_accessor :message

  def initialize(contentIdentifier = nil, message = nil)
    @contentIdentifier = contentIdentifier
    @message = message
  end
end

# {http://storage.tractis.com/}Properties
class Properties < ::Array
end

# {http://storage.tractis.com/}Property
#   xmlattr_name - SOAP::SOAPString
#   xmlattr_value - SOAP::SOAPString
class Property
  AttrName = XSD::QName.new(nil, "name")
  AttrValue = XSD::QName.new(nil, "value")

  def __xmlattr
    @__xmlattr ||= {}
  end

  def xmlattr_name
    __xmlattr[AttrName]
  end

  def xmlattr_name=(value)
    __xmlattr[AttrName] = value
  end

  def xmlattr_value
    __xmlattr[AttrValue]
  end

  def xmlattr_value=(value)
    __xmlattr[AttrValue] = value
  end

  def initialize
    @__xmlattr = {}
  end
end

# {http://storage.tractis.com/}ContentRecoverRequest
#   contentIdentifier - SOAP::SOAPString
#   properties - Properties
class ContentRecoverRequest
  attr_accessor :contentIdentifier
  attr_accessor :properties

  def initialize(contentIdentifier = nil, properties = nil)
    @contentIdentifier = contentIdentifier
    @properties = properties
  end
end

# {http://storage.tractis.com/}ContentRecoverResponse
#   protectedContent - ProtectedContent
#   serverMessage - SOAP::SOAPString
class ContentRecoverResponse
  attr_accessor :protectedContent
  attr_accessor :serverMessage

  def initialize(protectedContent = [], serverMessage = nil)
    @protectedContent = protectedContent
    @serverMessage = serverMessage
  end
end

# {http://storage.tractis.com/}ContentRemoveRequest
#   contentIdentifier - SOAP::SOAPString
#   properties - Properties
class ContentRemoveRequest
  attr_accessor :contentIdentifier
  attr_accessor :properties

  def initialize(contentIdentifier = nil, properties = nil)
    @contentIdentifier = contentIdentifier
    @properties = properties
  end
end

# {http://storage.tractis.com/}ContentRemoveResponse
#   protectedContent - ProtectedContent
#   serverMessage - SOAP::SOAPString
class ContentRemoveResponse
  attr_accessor :protectedContent
  attr_accessor :serverMessage

  def initialize(protectedContent = [], serverMessage = nil)
    @protectedContent = protectedContent
    @serverMessage = serverMessage
  end
end

# {http://storage.tractis.com/}ContentListRequest
#   properties - Properties
class ContentListRequest
  attr_accessor :properties

  def initialize(properties = nil)
    @properties = properties
  end
end

# {http://storage.tractis.com/}ContentListResponse
#   contentIdentifier - SOAP::SOAPString
#   serverMessage - SOAP::SOAPString
class ContentListResponse
  attr_accessor :contentIdentifier
  attr_accessor :serverMessage

  def initialize(contentIdentifier = [], serverMessage = nil)
    @contentIdentifier = contentIdentifier
    @serverMessage = serverMessage
  end
end

# {http://storage.tractis.com/}ProtectedContent
#   content - SOAP::SOAPBase64
#   contentIdentifier - SOAP::SOAPString
#   properties - Properties
#   protectionMeasure - ProtectionMeasure
class ProtectedContent
  attr_accessor :content
  attr_accessor :contentIdentifier
  attr_accessor :properties
  attr_accessor :protectionMeasure

  def initialize(content = nil, contentIdentifier = nil, properties = nil, protectionMeasure = [])
    @content = content
    @contentIdentifier = contentIdentifier
    @properties = properties
    @protectionMeasure = protectionMeasure
  end
end

# {http://storage.tractis.com/}ProtectionMeasure
#   protectionContent - SOAP::SOAPBase64
#   protectionType - SOAP::SOAPString
#   applicationDate - SOAP::SOAPDate
#   expirationDate - SOAP::SOAPDate
class ProtectionMeasure
  attr_accessor :protectionContent
  attr_accessor :protectionType
  attr_accessor :applicationDate
  attr_accessor :expirationDate

  def initialize(protectionContent = nil, protectionType = nil, applicationDate = nil, expirationDate = nil)
    @protectionContent = protectionContent
    @protectionType = protectionType
    @applicationDate = applicationDate
    @expirationDate = expirationDate
  end
end
