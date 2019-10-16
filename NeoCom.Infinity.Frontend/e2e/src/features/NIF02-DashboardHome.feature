@NIF02
Feature: NIF02-Display the character dashboard page.

    This page is where to show the Corporation data along with the Pilot data. Also there is a toolbar where to select the feature to work with. There are a set of dashboard pages, each one for a different feature plus this one that will not show any feature activated.

    @NIF02 @NIF02.01
    Scenario: [NIF02.01]-Check that the Dashboard Home Page has the application info panel
        Given I am on the Dashboard Home Page page
        When the page is activated
        Then there is a "appinfo-panel"
        And appinfo panel displays the next fields
            | app-name        | app-version | copyright               |
            | NEOCOM.INFINITY | 0.16.0-TEST | <environment.copyright> |

    @NIF02 @NIF02.01
    Scenario: [NIF02.01]-Check that the Dashboard Home Page has the server information panel
        Given I am on the Dashboard Home Page page
        When the page is activated
        Then there is a "serverinfo-panel"
        And serverinfo panel displays the next fields
            | server-name | server-status | capsuleers |
            | Tranquility | online        | 14.324     |
        And there is a "corporationpublicdata-panel"
        And there is a "pilotpublicdata-panel"
