@NIB04
Feature: [NIB04] Validate the authorization token response contract to match the structure expected on the client.

  Check that after a correct token authorization validation the structure and contents of the response are the contents
  expected by the clients.

  @NIB04.01 @Authorization
  Scenario: [NIB04.01] Validate the Validate Authorization Token Response.
    Given a request to the "Validate Authorization Token" endpoint with the next data
      | code         | state         | dataSource  |
      | -VALID-CODE- | -VALID-STATE- | tranquility |
    When the "Validate Authorization Token" request is processed
    Then the "Validate Authorization Token Response" has the next contents
      | responseType                       | jwtToken                                                                                                                                                                                                                                                                                                                                                                               | credential |
      | ValidateAuthorizationTokenResponse | eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6OTgzODQ3MjYsImFjY291bnROYW1lIjoiVGVzdGluZyBDaGFyYWN0ZXIgQWNjb3VudCIsImlzcyI6Ik5lb0NvbS5JbmZpbml0eS5CYWNrZW5kIiwidW5pcXVlSWQiOiJ0cmFucXVpbGl0eS85MzgxMzMxMCIsInBpbG90SWQiOjkzODEzMzEwfQ.eJvBC2144s7sKv5rxSUVEjNbP2BpQJlJhmTOu4AJ9eJj9so_WcrAthbvwgYM4BqyBSNZAjw7bVegieWqx8IX8Q | Credential |
