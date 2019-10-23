@NIB05
Feature: [NIB05] This set of scenarios will test the public universe api access through the backend server.

  This set of scenarios will test the public universe api access through the backend server. All those endpoint will not have
  authenticated because they access ESI endpoints that do not require authorization.

  @NIB05.01 @Universe
  Scenario: [NIB05.01] Access the Eve Online server status.
    Given a request to the "Get Server Status" endpoint with the next data
      | dataSource  |
      | Tranquility |
    When the "Get Server Status" request is processed
    Then the response status code is 200
    And the response has a server block with the next data
      | players | server_version | start_time           |
      | 25003   | 1589237        | 2019-10-22T11:04:50Z |
