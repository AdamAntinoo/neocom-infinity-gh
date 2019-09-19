@NIB01
Feature: [NIB01] Validate the authorization token from ESI OAuth2 service.

  Check that we arrive to this endpoint after requesting an authorization token from ESI OAuth2 authentication service.
  Once we arrive to this endpoint we should check that the source is the expected source identifier and that we
  can create a Credential from the token received by exchanging that token by a refresh token.

  @NIB01.01 @Authorization
  Scenario: [NIB01.01] Detect the correct state at the endpoint.
	Given a request to the "Validate Authorization Token" endpoint with the next data
	  | code          | state         | dataSource |
	  | -not-applies- | -valid-state- |            |
	And the state field matches "-valid-state-"
	When the "Validate Authorization Token" request is processed
	Then the response status code is 200
	And the "Validate Authorization Token" response contains a valid Credential
