@NIF01
Feature: NIF01-Display the login autorization confirmation page

    Display the application header along with the progress indicator awaiting for the token authorization
    validation.

    @NIF01 @NIF01.01
    Scenario: NIF01.01-Check that the Login Validation Page has all the panels expected
        Given I am on the "Login Validation Page" page
        When the request has the code "-CODE-TO-CHECK-ALL-PANELS-" and state "-INVALID-STATE-"
        Then the page shows 3 panels
        And there is a "appinfo-panel"
        And there is a "loginvalidationprogress-panel"
        And there is a "loginvalidationexception-panel"

    @NIF01 @NIF01.02
    Scenario: NIF01.02-Check the Application Info panel contents
        Given I am on the "Login Validation Page" page
        When the request has the code "-CODE-TO-CHECK-ALL-PANELS-" and state "-INVALID-STATE-"
        Then there is a "appinfo-panel"
        And panel displays the next fields
            | app-name        | app-version | copyright               |
            | NeoCom.Infinity | 0.16.0-TEST | <environment.copyright> |

    @NIF01 @NIF01.03
    Scenario: NIF01.03-Check that the Login Validation Page has all only two panels when correct parameters
        Given I am on the "Login Validation Page" page
        When the request has the code "-CODE-VALID-" and state "LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct"
        Then the page shows 3 panels
        And there is a "appinfo-panel"
        And there is a "loginvalidationprogress-panel"
        # And there is a "loginvalidationexception-panel"

    # Scenario: NIF02.04-Check the Application header contents
    #     Given I am on the "Login Token Authorization Validation" page
    #     When I wait for validation confirmation
    #     Then I should see a "ApplicationHeader" panel
    #     And The target panel displays the next fields
    #         | applicationName | applicationVersion |
    #         | NEOCOM          | <appVersion>       |

    # Scenario: NIF02.02-Display the token authorization validation waiting indicator
    #     Given I am on the "Login Token Authorization Validation" page
    #     When I wait for validation confirmation
    #     Then I should see a "TokenAuthorizationProgress" panel
    #     And The "ApplicationHeader" panel "title" field displays "Verifying Authorization Token..."

    # Scenario: NIF02.03-When the confirmation arrives the response should return a JWT token and a Credential
    #     Given I am on the "Login Token Authorization Validation" page
    #     When The validation response arrives
    #     Then The response has a valid JWT token
    #     And The response has a Credential
