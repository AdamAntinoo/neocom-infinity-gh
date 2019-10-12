@NIB03
Feature: [NIB03] Get the selected Pilot public data

  Retrieve the Pilot public accessible data. This should report the first level of information for game characters.

  @NIB03.01 @Pilot
  Scenario: [NIB02.03] Search for the requested Pilot and return a well formed Pilot data response.
    Given a request to the "Get Pilot Data" endpoint with the next data
      | pilotId  |
      | 92223647 |
    And "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6OTM4MTMzMTAsImFjY291bnROYW1lIjoiVGVzdGluZyBDaGFyYWN0ZXIgQWNjb3VudCIsImlzcyI6Ik5lb0NvbS5JbmZpbml0eS5CYWNrZW5kIiwidW5pcXVlSWQiOiJ0cmFucXVpbGl0eS85MzgxMzMxMCIsInBpbG90SWQiOjkyMjIzNjQ3fQ.srW3N_Bp3DjoEZFw4V-BoV7WDLnieKNbzs5iAFmflmBByw9xvVLabsd2GNohDI5u4lJdnskBWUgetUCr22ZLUQ  " authorization token
    When the "Get Pilot Data" request is processed
    Then the response status code is 200
    And the response has a pilot block with the next data
      | pilotId  | corporationId | name        |
      | 92223647 | 93813310      | Beth Ripley |
