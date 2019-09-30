@NIF01
Feature: NIF01-Display the login autorization confirmation page

    Display the application header along with the progress indicator awaiting for the token authorization
    validation.

    @NIF01 @NIF01.01
    Scenario: NIF01.01-Check that the Login Validation Page has all the panels expected
        Given I am on the "Login Validation Page" page
        When the request has the code "-CODE-TO-CHECK-ALL-PANELS-" and state "-INVALID-STATE-"
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
        And there is a "appinfo-panel"
        And there is a "loginvalidationprogress-panel"
        And there is not a "loginvalidationexception-panel"


    @NIF01 @NIF01.04
    Scenario: NIF01.04-When using a testing code the progress indicator shows the progress for the code authentication
        Given I am on the "Login Validation Page" page
        When the request has the code "-VALIDATE-PROGRESS-" and state "LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct"
        Then there is a "loginvalidationprogress-panel"
        And loginvalidationprogress panel displays the next fields
            | app-name        | app-version | copyright               |
            | NeoCom.Infinity | 0.16.0-TEST | <environment.copyright> |

    # Scenario: NIF02.03-When the confirmation arrives the response should return a JWT token and a Credential
    #     Given I am on the "Login Token Authorization Validation" page
    #     When The validation response arrives
    #     Then The response has a valid JWT token
    #     And The response has a Credential
