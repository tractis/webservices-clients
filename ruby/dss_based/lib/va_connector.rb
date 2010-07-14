#Allows basic operations vs validation authority
require 'net/http'
class Net::HTTP
  alias_method :old_initialize, :initialize
  def initialize(*args)
    old_initialize(*args)
    @ssl_context = OpenSSL::SSL::SSLContext.new
    @ssl_context.verify_mode = OpenSSL::SSL::VERIFY_NONE
  end
end

#Allows basic operations vs validation authority
require 'uri'
require 'net/https'
require 'credentials'
class VaConnector


def initialize(url)
	@url = URI.parse(url)
end

def send_request(request)
    req = Net::HTTP::Post.new(@url.path)
    req.content_type = "text/xml"
    req.basic_auth Credentials::USER, Credentials::PASS
    data = request.to_s


    http_client = Net::HTTP.new(@url.host, @url.port)
    http_client.use_ssl = (@url.scheme == 'https')
    response = http_client.start do |http|
                http.request(req, data)
               end


    case response
	   when Net::HTTPSuccess
	      service_response = REXML::Document.new(response.read_body)
	   when Net::HTTPUnauthorized
	      raise "Server report unauthorized, check credentials at credentials.rb"
	    else
	      raise "Server reported error #{response.status}"
	    end
    end

end
