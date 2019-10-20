@NIB04
Feature: [NIB04] Validate the response contracts of the published API.
  Validate

  Check that after a correct token authorization validation the authorization token response contract matches the structure
  expected by the client.
  Check that the Corporation data response has all the data and serializations expected by the client.
  Check that the Pilot response serializes all the pilot fields expected by the frontend client.

  @NIB04.01 @Contracts
  Scenario: [NIB04.01] Validate the Validate Authorization Token Response contract.
    Given a request to the "Validate Authorization Token" endpoint with the next data
      | code         | state         | dataSource  |
      | -VALID-CODE- | -VALID-STATE- | tranquility |
    When the "Validate Authorization Token" request is processed
    Then the "Validate Authorization Token Response" has the next contents
      | responseType                       | credential | accountId | accountName               | dataSource  | corporationId | assetsCount | walletBalance | miningResourcesEstimatedValue | jwtToken                                                                                                                                                                                                                                                                                                                                                                               |
      | ValidateAuthorizationTokenResponse | Credential | 93813310  | Testing Character Account | tranquility | 98384726      | 0           | 0.0           | 0.0                           | eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6OTgzODQ3MjYsImFjY291bnROYW1lIjoiVGVzdGluZyBDaGFyYWN0ZXIgQWNjb3VudCIsImlzcyI6Ik5lb0NvbS5JbmZpbml0eS5CYWNrZW5kIiwidW5pcXVlSWQiOiJ0cmFucXVpbGl0eS85MzgxMzMxMCIsInBpbG90SWQiOjkzODEzMzEwfQ.eJvBC2144s7sKv5rxSUVEjNbP2BpQJlJhmTOu4AJ9eJj9so_WcrAthbvwgYM4BqyBSNZAjw7bVegieWqx8IX8Q |

