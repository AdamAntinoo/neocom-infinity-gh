Feature: smoke tests

  Background: full end to end test with app
    Given the app has started

  Scenario: app has health
    When I invoke the health endpoint
    Then the status of the app is UP
    
  Scenario: app has info
    When I invoke the info endpoint
    Then the response has "cards-transactions" as the app name