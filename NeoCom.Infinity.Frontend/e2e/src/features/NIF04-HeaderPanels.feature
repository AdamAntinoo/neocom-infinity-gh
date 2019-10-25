@NIF04 @Header
Feature: [NIF04]-Check the visual contents for the panels declared on the Header Module.

    Use any page available to render the panels defined on the Header Module on all possible cases to check that the rendering evaluations behave as expected by the user interface.

    @NIF04 @NIF04.01
    Scenario: [NIF04.01]-Validate the Server Info Panel contents. Online case.
        Given I am on the Dashboard Home Page page
        When the page is activated with the request id "SERVER-INFO-ONLINE"
        Then there is a "serverinfo-panel"
        And Header serverinfo-panel displays the next fields
            | server-name | server-status | capsuleers | server-laststart     |
            | Tranquility | ONLINE        | 14.324     | 2019-10-16T11:06:17Z |

    @NIF04 @NIF04.02
    Scenario: [NIF04.02]-Validate the Server Info Panel contents. Offline case.
        Given I am on the Dashboard Home Page page
        When the page is activated with the request id "SERVER-INFO-OFFLINE"
        Then there is a "serverinfo-panel"
        And Header serverinfo-panel displays the next fields
            | server-name | server-status | capsuleers | server-laststart     |
            | Tranquility | ONLINE        | 14.324     | 2019-10-16T11:06:17Z |

# @NIF04 @NIF04.03
# Scenario: [NIF04.3]-Validate the Server Info Panel contents. Backend error case.
#     Given I am on the Dashboard Home Page page
#     When the page is activated with the request id "SERVER-INFO-BACKEND-ERROR"
#     Then there is a "serverinfo-panel"
#     And Header serverinfo-panel displays the next fields
#         | server-name | server-status | capsuleers | server-laststart     |
#         | Tranquility | ONLINE        | 14.324     | 2019-10-16T11:06:17Z |

# @NIF02 @NIF02.03
# Scenario: [NIF02.03]-Check that the Dashboard Home Page has the corporation data panel.
#     Given I am on the Dashboard Home Page page
#     When the page is activated
#     Then there is a "corporationdata-panel"
#     And Dashboard Home Page corporationdata-panel displays the next fields
#         | corporation-name    | corporation-id | corporation-ticker | corporation-members-count |
#         | Industrias Machaque | [98384726]     | [IN.MA]            | 3 MEMBERS                 |

# @NIF02 @NIF02.04
# Scenario: [NIF02.04]-Check that the Dashboard Home Page has the alliance data panel.
#     Given I am on the Dashboard Home Page page
#     When the page is activated with the next corporation
#         | corporationId |
#         | 123           |
#     Then there is a "alliance-panel"
#     And alliance panel displays the next fields
#         | corporation-name    | corporation-id | corporation-ticker | corporation-members-count |
#         | Industrias Machaque | [98384726]     | [IN.MA]            | 3 MEMBERS                 |

# @NIF02 @NIF02.05
# Scenario: [NIF02.05]-Check that the Dashboard Home Page has an empty alliance data panel.
#     Given I am on the Dashboard Home Page page
#     When the page is activated with the next corporation
#         | corporationId |
#         | 123           |
#     Then there is a "alliance-panel"
#     And alliance panel displays the next fields
#         | alliance-name  |
#         | NOT ASSOCIATED |

# @NIF02 @NIF02.06
# Scenario: [NIF02.06]-Check that the Dashboard Home Page has the pilot data panel.
#     Given I am on the Dashboard Home Page page
#     When the page is activated
#     Then there is a "pilotpublicdata-panel"
#     And pilotpublicdata panel displays the next fields
#         | pilot-name  | pilot-id   | pilot-race | pilot-ancestry | pilot-bloodline | pilot-gender |
#         | Beth Ripley | [92223647] | Minmatar   | Workers        | Brutor          | FEMALE       |
