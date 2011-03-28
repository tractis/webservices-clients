require 'lib/content_storage_service_client'

client = ContentStorageServiceClient.new

client.recover_by_properties({:type=>"File"})
