@NIF01
Feature: NIF01-Check the render of the application name and version
    Display application name and the application version inside a white panel

    @NIF01.01
    Scenario: NIF01.01-Check the Application Info panel contents
        Given I am on the "Login Validation Page" page
        When the request has the code "-CODE-TO-CHECK-ALL-PANELS-"
        Then there is a "appinfo-panel"
        And panel displays the next fields
            | app-name        | app-version | copyright           |
            | neocom-infinity | 0.16.0      | <environment.copyright> |
