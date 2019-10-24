@NIF02
Feature: NIF02-Display the character dashboard page.

    This page is where to show the Corporation data along with the Pilot data. Also there is a toolbar where to select the feature to work with. There are a set of dashboard pages, each one for a different feature plus this one that will not show any feature activated.

    @NIF02 @NIF02.01
    Scenario: [NIF02.01]-Check that the Dashboard Home Page has the application info panel.
        Given I am on the Dashboard Home Page page
        When the page is activated
        Then there is a "appinfo-panel"
        And Dashboard Home Page appinfo-panel displays the next fields
            | app-name              | app-version              | copyright               |
            | <environment.appName> | <environment.appVersion> | <environment.copyright> |

    @NIF02 @NIF02.02
    Scenario: [NIF02.02]-Check that the Dashboard Home Page has the server information panel.
        Given I am on the Dashboard Home Page page
        When the page is activated
        Then there is a "serverinfo-panel"
        And Dashboard Home Page serverinfo-panel displays the next fields
            | server-name | server-status | capsuleers | server-laststart     |
            | Tranquility | ONLINE        | 14.324     | 2019-10-16T11:06:17Z |

    @NIF02 @NIF02.03
    Scenario: [NIF02.03]-Check that the Dashboard Home Page has the corporation data panel.
        Given I am on the Dashboard Home Page page
        When the page is activated
        Then there is a "corporationdata-panel"
        And Dashboard Home Page corporationdata-panel displays the next fields
            | corporation-name    | corporation-id | corporation-ticker | corporation-members-count |
            | Industrias Machaque | [98384726]     | [IN.MA]            | 3 MEMBERS                 |

    @NIF02 @NIF02.04
    Scenario: [NIF02.04]-Check that the Dashboard Home Page has the alliance data panel.
        Given I am on the Dashboard Home Page page
        When the page is activated with the next corporation
            | corporationId |
            | 123           |
        Then there is a "alliance-panel"
        And alliance panel displays the next fields
            | corporation-name    | corporation-id | corporation-ticker | corporation-members-count |
            | Industrias Machaque | [98384726]     | [IN.MA]            | 3 MEMBERS                 |

    @NIF02 @NIF02.05
    Scenario: [NIF02.05]-Check that the Dashboard Home Page has an empty alliance data panel.
        Given I am on the Dashboard Home Page page
        When the page is activated with the next corporation
            | corporationId |
            | 123           |
        Then there is a "alliance-panel"
        And alliance panel displays the next fields
            | alliance-name  |
            | NOT ASSOCIATED |

    @NIF02 @NIF02.06
    Scenario: [NIF02.06]-Check that the Dashboard Home Page has the pilot data panel.
        Given I am on the Dashboard Home Page page
        When the page is activated
        Then there is a "pilotpublicdata-panel"
        And pilotpublicdata panel displays the next fields
            | pilot-name  | pilot-id   | pilot-race | pilot-ancestry | pilot-bloodline | pilot-gender |
            | Beth Ripley | [92223647] | Minmatar   | Workers        | Brutor          | FEMALE       |
