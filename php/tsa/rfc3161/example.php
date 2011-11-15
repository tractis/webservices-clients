<?php
require_once "trusted_timestamps.php";

// Create an API Key here: https://www.tractis.com/webservices/tsa/apikeys
// Copy the Identifier and Secret here:
$api_identifier = "your_api_identifier";
$api_secret = "your_api_secret";
$tsa_cert_chain_file = "chain.txt";

$my_hash = sha1("content to stamp");

$requestfile_path = TrustedTimestamps::createRequestfile($my_hash);

$response = TrustedTimestamps::signRequestfile($requestfile_path, "https://api.tractis.com/rfc3161tsa", $api_identifier, $api_secret);
print_r($response);
/*
Array
(
    [response_string] => Shitload of text (base64-encoded Timestamp-Response of the TSA)
    [response_time] => 1299098823
)
*/

echo TrustedTimestamps::getTimestampFromAnswer($response['response_string']); //1299098823

$validate = TrustedTimestamps::validate($my_hash, $response['response_string'], $response['response_time'], $tsa_cert_chain_file);
print_r("\nValidation result\n");
var_dump($validate); //bool(true)

//now with an incorrect hash. Same goes for a manipulated response string or response time
$validate = TrustedTimestamps::validate(sha1("im not the right hash"), $response['response_string'], $response['response_time'], $tsa_cert_chain_file);
print_r("\nValidation result after content manipulation\n");
var_dump($validate); //bool(false)

?>