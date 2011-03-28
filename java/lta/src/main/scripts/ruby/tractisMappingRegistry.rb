require 'tractis.rb'
require 'soap/mapping'

module TractisMappingRegistry
  EncodedRegistry = ::SOAP::Mapping::EncodedRegistry.new
  LiteralRegistry = ::SOAP::Mapping::LiteralRegistry.new
  NsStorageTractisCom = "http://storage.tractis.com/"

  LiteralRegistry.register(
    :class => ContentStoreRequest,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "ContentStoreRequest"),
    :schema_element => [
      ["content", ["SOAP::SOAPBase64", XSD::QName.new(NsStorageTractisCom, "Content")]],
      ["properties", ["Properties", XSD::QName.new(NsStorageTractisCom, "Properties")]]
    ]
  )

  LiteralRegistry.register(
    :class => ContentStoreResponse,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "ContentStoreResponse"),
    :schema_element => [
      ["contentIdentifier", ["SOAP::SOAPString", XSD::QName.new(NsStorageTractisCom, "ContentIdentifier")]],
      ["message", ["SOAP::SOAPString", XSD::QName.new(NsStorageTractisCom, "Message")]]
    ]
  )

  LiteralRegistry.register(
    :class => Properties,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "Properties"),
    :schema_element => [
      ["property", ["Property[]", XSD::QName.new(NsStorageTractisCom, "Property")]]
    ]
  )

  LiteralRegistry.register(
    :class => Property,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "Property"),
    :schema_element => [],
    :schema_attribute => {
      XSD::QName.new(nil, "name") => "SOAP::SOAPString",
      XSD::QName.new(nil, "value") => "SOAP::SOAPString"
    }
  )

  LiteralRegistry.register(
    :class => ContentRecoverRequest,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "ContentRecoverRequest"),
    :schema_element => [ :choice,
      ["contentIdentifier", ["SOAP::SOAPString", XSD::QName.new(NsStorageTractisCom, "ContentIdentifier")]],
      ["properties", ["Properties", XSD::QName.new(NsStorageTractisCom, "Properties")]]
    ]
  )

  LiteralRegistry.register(
    :class => ContentRecoverResponse,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "ContentRecoverResponse"),
    :schema_element => [ :choice,
      [
        ["protectedContent", ["ProtectedContent[]", XSD::QName.new(NsStorageTractisCom, "ProtectedContent")]]
      ],
      ["serverMessage", ["SOAP::SOAPString", XSD::QName.new(NsStorageTractisCom, "ServerMessage")]]
    ]
  )

  LiteralRegistry.register(
    :class => ContentRemoveRequest,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "ContentRemoveRequest"),
    :schema_element => [ :choice,
      ["contentIdentifier", ["SOAP::SOAPString", XSD::QName.new(NsStorageTractisCom, "ContentIdentifier")]],
      ["properties", ["Properties", XSD::QName.new(NsStorageTractisCom, "Properties")]]
    ]
  )

  LiteralRegistry.register(
    :class => ContentRemoveResponse,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "ContentRemoveResponse"),
    :schema_element => [ :choice,
      [
        ["protectedContent", ["ProtectedContent[]", XSD::QName.new(NsStorageTractisCom, "ProtectedContent")]]
      ],
      ["serverMessage", ["SOAP::SOAPString", XSD::QName.new(NsStorageTractisCom, "ServerMessage")]]
    ]
  )

  LiteralRegistry.register(
    :class => ContentListRequest,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "ContentListRequest"),
    :schema_element => [ :choice,
      [
        ["properties", ["Properties", XSD::QName.new(NsStorageTractisCom, "Properties")]]
      ]
    ]
  )

  LiteralRegistry.register(
    :class => ContentListResponse,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "ContentListResponse"),
    :schema_element => [ :choice,
      [
        ["contentIdentifier", ["SOAP::SOAPString[]", XSD::QName.new(NsStorageTractisCom, "ContentIdentifier")]]
      ],
      ["serverMessage", ["SOAP::SOAPString", XSD::QName.new(NsStorageTractisCom, "ServerMessage")]]
    ]
  )

  LiteralRegistry.register(
    :class => ProtectedContent,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "ProtectedContent"),
    :schema_element => [
      ["content", ["SOAP::SOAPBase64", XSD::QName.new(NsStorageTractisCom, "Content")]],
      ["contentIdentifier", ["SOAP::SOAPString", XSD::QName.new(NsStorageTractisCom, "ContentIdentifier")]],
      ["properties", ["Properties", XSD::QName.new(NsStorageTractisCom, "Properties")]],
      [
        ["protectionMeasure", ["ProtectionMeasure[]", XSD::QName.new(NsStorageTractisCom, "ProtectionMeasure")]]
      ]
    ]
  )

  LiteralRegistry.register(
    :class => ProtectionMeasure,
    :schema_name => XSD::QName.new(NsStorageTractisCom, "ProtectionMeasure"),
    :schema_element => [
      ["protectionContent", ["SOAP::SOAPBase64", XSD::QName.new(NsStorageTractisCom, "ProtectionContent")]],
      ["protectionType", ["SOAP::SOAPString", XSD::QName.new(NsStorageTractisCom, "ProtectionType")]],
      ["applicationDate", ["SOAP::SOAPDateTime", XSD::QName.new(NsStorageTractisCom, "ApplicationDate")]],
      ["expirationDate", ["SOAP::SOAPDateTime", XSD::QName.new(NsStorageTractisCom, "ExpirationDate")]]
    ]
  )
end
