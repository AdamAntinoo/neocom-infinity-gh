@NIB02
Feature: [NIB02] Get the selected Corporation first level data

  Retrieve the Corporation data to be accessed on prime access. Check that the caller is authorized and that the authorization
  allows the access to the selected Corporation.

  @NIB02.01 @Corporation
  Scenario: [NIB02.01] The authorization token does not exist on the request.
    Given a request to the "Get Corporation Data" endpoint with the next data
      | corporationId |
      | 123456        |
    And "<null>" authorization token
    When the "Get Corporation Data" request is processed
    Then the response status code is 404

  @NIB02.02 @Corporation
  Scenario: [NIB02.02] The authorization token corporation identifier does not match the requested corporation.
    Given a request to the "Get Corporation Data" endpoint with the next data
      | corporationId |
      | 123456        |
    And "FFGG" authorization token
    When the "Get Corporation Data" request is processed
    Then the response status code is 404
    And the exception message is ""
