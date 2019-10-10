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
    Then the response status code is 403

  @NIB02.02 @Corporation
  Scenario: [NIB02.02] The authorization token corporation identifier does not match the requested corporation.
    Given a request to the "Get Corporation Data" endpoint with the next data
      | corporationId |
      | 123456        |
    And "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6OTM4MTMzMTAsImFjY291bnROYW1lIjoiVGVzdGluZyBDaGFyYWN0ZXIgQWNjb3VudCIsImlzcyI6Ik5lb0NvbS5JbmZpbml0eS5CYWNrZW5kIiwidW5pcXVlSWQiOiJ0cmFucXVpbGl0eS85MzgxMzMxMCIsInBpbG90SWQiOjkzODEzMzEwfQ.g4VqcRxThHb9g0Ln24yix8zbu7kIplA4oIOuU5LM53v0pvEtrotWaBAF8zS9zELmm2_10QJhhdSdamSI9ntjdw" authorization token
    When the "Get Corporation Data" request is processed
    Then the response status code is 403
#    And the exception message is "The corporation requested is not authorized to the requester."

  @NIB02.03 @Corporation
  Scenario: [NIB02.03] The authorization matches and the response is a valid Corporation data block.
    Given a request to the "Get Corporation Data" endpoint with the next data
      | corporationId |
      | 93813310      |
    And "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6OTM4MTMzMTAsImFjY291bnROYW1lIjoiVGVzdGluZyBDaGFyYWN0ZXIgQWNjb3VudCIsImlzcyI6Ik5lb0NvbS5JbmZpbml0eS5CYWNrZW5kIiwidW5pcXVlSWQiOiJ0cmFucXVpbGl0eS85MzgxMzMxMCIsInBpbG90SWQiOjkzODEzMzEwfQ.g4VqcRxThHb9g0Ln24yix8zbu7kIplA4oIOuU5LM53v0pvEtrotWaBAF8zS9zELmm2_10QJhhdSdamSI9ntjdw" authorization token
    When the "Get Corporation Data" request is processed
    Then the response status code is 200
#    And the exception message is "The corporation requested is not authorized to the requester."
