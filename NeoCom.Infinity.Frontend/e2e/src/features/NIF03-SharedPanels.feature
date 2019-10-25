@NIF03 @Shared
Feature: [NIF03]-Check the visual contents for the panels declared on the Shared Module.

    Use any page available to render the panels defined on the Shared Module on all possible cases to check that the rendering evaluations behave as expected by the user interface.

    @NIF03 @NIF03.01
    Scenario: [NIF03.01]-Validate the Application Info Panel contents. Single case.
        Given I am on the Dashboard Home Page page
        When the page is activated
        Then there is a "appinfo-panel"
        And Shared appinfo-panel displays the next fields
            | app-name              | app-version              | copyright               |
            | <environment.appName> | <environment.appVersion> | <environment.copyright> |

# @NIF01 @NIF01.02
# Scenario: [NIF01.02]-Check that the Login Validation Page has the validation progress panel
#     Given I am on the Login Validation Page page
#     When the request has the code "-CODE-TO-CHECK-PROGRESS-PANEL-" and state "-INVALID-STATE-"
#     Then there is a "loginvalidationprogress-panel"
#     And loginvalidationprogress panel displays the next fields
#         | progress-label                     | progress-code                  |
#         | Waiting for code authentication... | -CODE-TO-CHECK-PROGRESS-PANEL- |

# @NIF01 @NIF01.03
# Scenario: [NIF01.03]-Check that the Login Validation Page has the exception information panel
#     Given I am on the Login Validation Page page
#     When the request has the code "-CODE-TO-CHECK-VALIDATION-EXCEPTION-PANEL-" and state "-INVALID-STATE-"
#     Then there is a "loginvalidationexception-panel"
#     And loginvalidationexception panel displays the next fields
#         | exception-message                                      |
#         | The request state does not match. Caller not verified. |
