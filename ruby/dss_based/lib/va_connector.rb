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

    response = Net::HTTP.new(@url.host, @url.port).start {|http| http.request(req, data) }

    case response
	   when Net::HTTPSuccess
	      service_response = REXML::Document.new(response.read_body)
	    else
	      raise "Server reported error #{response.status}"
	    end
    end

end
