Feature: NIF02-Display the login autorization confirmation page

    Display the application header along with the progress indicator awaiting for the token authorization
    validation.

    @NIF02 @NIF02.01
    Scenario: NIF02.01-Check that the Login Validation Page has all the panels expected
        Given I am on the "Login Validation Page" page
        When the request has the code "-CODE-TO-CHECK-ALL-PANELS-"
        Then the page shows 3 panels
        And there is a "appinfo-panel"
        And there is a "loginvalidationprogress-panel"
        And there is a "loginvalidationexception-panel"

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
