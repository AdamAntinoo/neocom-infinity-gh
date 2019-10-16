@NIF01
Feature: [NIF01]-Display the login autorization confirmation page

    Display the application header along with the progress indicator awaiting for the token authorization
    validation.

    @NIF01 @NIF01.01
    Scenario: [NIF01.01]-Check that the Login Validation Page has the application info panel
        Given I am on the "Login Validation Page" page
        When the request has the code "-CODE-TO-CHECK-APP-INFO-" and state "-INVALID-STATE-"
        Then there is a "appinfo-panel"
        And appinfo panel displays the next fields
            | app-name        | app-version | copyright               |
            | neocom.infinity | 0.16.0-TEST | <environment.copyright> |

    @NIF01 @NIF01.02
    Scenario: [NIF01.02]-Check that the Login Validation Page has the validation progress panel
        Given I am on the "Login Validation Page" page
        When the request has the code "-CODE-TO-CHECK-PROGRESS-PANEL-" and state "-INVALID-STATE-"
        Then there is a "loginvalidationprogress-panel"
        And loginvalidationprogress panel displays the next fields
            | progress-label                     | progress-code              |
            | Waiting for code authentication... | -CODE-TO-CHECK-ALL-PANELS- |

    @NIF01 @NIF01.03
    Scenario: [NIF01.03]-Check that the Login Validation Page has the exception information panel
        Given I am on the "Login Validation Page" page
        When the request has the code "-CODE-TO-CHECK-VALIDATION-EXCEPTION-PANEL-" and state "-INVALID-STATE-"
        Then there is a "loginvalidationexception-panel"
        And loginvalidationexception panel displays the next fields
            | exception-message                                      |
            | The request state does not match. Caller not verified. |

    @NIF01 @NIF01.04
    Scenario: [NIF01.04]-Check that if the validation is valid we move to the Dashboard page
        Given I am on the "Login Validation Page" page
        When the request has the code "-CODE-VALID-" and state "LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct"
        Then there is a "appinfo-panel"
        And there is a "loginvalidationprogress-panel"
        And the route moves to the "Dashboard Home Page"

# @NIF01 @NIF01.03
# Scenario: NIF01.03-Check that the Login Validation Page has all only two panels when correct parameters and that should return a valid JWT token along a Credential
#     Given I am on the "Login Validation Page" page
#     When the request has the code "-CODE-VALID-" and state "LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct"
#     And there is a "appinfo-panel"
#     And there is a "loginvalidationprogress-panel"
# And there is not a "loginvalidationexception-panel"
# And the response has a valid JWT token
# And the response has a Credential

# @NIF01 @NIF01.04
# Scenario: NIF01.04-Validate that the JWT token is stored on the persistent client repository and that contains the expected values. Also check that contents match with Credental contents.
#     Given I am on the "Login Validation Page" page
#     When the request has the code "-CODE-VALID-" and state "LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct"
#     # And The response has a valid JWT token
#     And the JWT token with key "-JWTTOKEN_KEY-" has the next contents
#         | accountName  | uniqueId        |
#         | Adam Antinoo | tranquility/123 |
#     And the Credential has the next contents
#         | accountId | accountName  | uniqueId        |
#         | 123       | Adam Antinoo | tranquility/123 |

# @NIF01 @NIF01.05
# Scenario: NIF01.04-Validate that the JWT token is stored on the persistent client repository and that contains the expected values. Also check that contents match with Credental contents.
#     Given I am on the "Login Validation Page" page
#     When the request has the code "-CODE-VALID-" and state "LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct"
#     And after the validations and process
#     Then the page jumps to the "Dashboard Page" page

# @NIF01 @NIF01.04
# Scenario: NIF01.04-When using a testing code the progress indicator shows the progress for the code authentication
#     Given I am on the "Login Validation Page" page
#     When the request has the code "-VALIDATE-PROGRESS-" and state "LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct"

# @NIF01 @NIF01.05
# Scenario: NIF01.05-When the confirmation arrives the response should return a JWT token and a Credential
#     Given I am on the "Login Validation Page" page
#     When the request has the code "-CODE-VALID-" and state "LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct"
