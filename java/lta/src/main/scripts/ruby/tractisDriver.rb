require 'tractis.rb'
require 'tractisMappingRegistry.rb'
require 'soap/rpc/driver'

class SOAPport < ::SOAP::RPC::Driver
  DefaultEndpointUrl = "https://api.tractis.com/lta"

  Methods = [
    [ nil,
      "store",
      [ ["in", "ContentStoreRequest", ["::SOAP::SOAPElement", "http://storage.tractis.com/", "ContentStoreRequest"]],
        ["out", "ContentStoreResponse", ["::SOAP::SOAPElement", "http://storage.tractis.com/", "ContentStoreResponse"]] ],
      { :request_style =>  :document, :request_use =>  :literal,
        :response_style => :document, :response_use => :literal,
        :faults => {} }
    ],
    [ nil,
      "recover",
      [ ["in", "ContentRecoverRequest", ["::SOAP::SOAPElement", "http://storage.tractis.com/", "ContentRecoverRequest"]],
        ["out", "ContentRecoverResponse", ["::SOAP::SOAPElement", "http://storage.tractis.com/", "ContentRecoverResponse"]] ],
      { :request_style =>  :document, :request_use =>  :literal,
        :response_style => :document, :response_use => :literal,
        :faults => {} }
    ],
    [ nil,
      "remove",
      [ ["in", "ContentRemoveRequest", ["::SOAP::SOAPElement", "http://storage.tractis.com/", "ContentRemoveRequest"]],
        ["out", "ContentRemoveResponse", ["::SOAP::SOAPElement", "http://storage.tractis.com/", "ContentRemoveResponse"]] ],
      { :request_style =>  :document, :request_use =>  :literal,
        :response_style => :document, :response_use => :literal,
        :faults => {} }
    ],
    [ nil,
      "list",
      [ ["in", "ContentListRequest", ["::SOAP::SOAPElement", "http://storage.tractis.com/", "ContentListRequest"]],
        ["out", "ContentListResponse", ["::SOAP::SOAPElement", "http://storage.tractis.com/", "ContentListResponse"]] ],
      { :request_style =>  :document, :request_use =>  :literal,
        :response_style => :document, :response_use => :literal,
        :faults => {} }
    ]
  ]

  def initialize(endpoint_url = nil)
    endpoint_url ||= DefaultEndpointUrl
    super(endpoint_url, nil)
    self.mapping_registry = TractisMappingRegistry::EncodedRegistry
    self.literal_mapping_registry = TractisMappingRegistry::LiteralRegistry
    init_methods
  end

private

  def init_methods
    Methods.each do |definitions|
      opt = definitions.last
      if opt[:request_style] == :document
        add_document_operation(*definitions)
      else
        add_rpc_operation(*definitions)
        qname = definitions[0]
        name = definitions[2]
        if qname.name != name and qname.name.capitalize == name.capitalize
          ::SOAP::Mapping.define_singleton_method(self, qname.name) do |*arg|
            __send__(name, *arg)
          end
        end
      end
    end
  end
end

