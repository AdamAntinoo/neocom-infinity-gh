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
    And "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiYWNjb3VudE5hbWUiOiJUZXN0aW5nIENoYXJhY3RlciBBY2NvdW50IiwiaXNzIjoiTmVvQ29tLkluZmluaXR5LkJhY2tlbmQiLCJ1bmlxdWVJZCI6InRyYW5xdWlsaXR5LzkzODEzMzEwIn0.oc7hROjmsBG7s0D_ZD30B3EJOpz2B_mWmZXgVBcO_NpHmQ5fBLJ028xAaE0aiMrAcc_OQDtzTZrsjFTZZufAlg" authorization token
    When the "Get Corporation Data" request is processed
    Then the response status code is 403
    And the exception message is "Access Denied"
