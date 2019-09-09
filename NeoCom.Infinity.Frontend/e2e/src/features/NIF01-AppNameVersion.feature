Feature: NIF01-Check the render of the application name and version
  Display application name and the application version inside a white panel

  Scenario: NIF01.01-Check the Application name rendered
    Given I am on the login landing page
    When I do nothing
    Then I should see a "white" panel
    And The panel should render the application name
    And The panel should render the application version
    And The panel should render the copyright notice
