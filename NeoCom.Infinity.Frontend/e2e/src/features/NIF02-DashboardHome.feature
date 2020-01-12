@NIF02 @DashboardHome
Feature: [NIF02]-Display the character dashboard page.

    This page is where to show the Corporation data along with the Pilot data. Also there is a toolbar where to select the feature to work with. There are a set of dashboard pages, each one for a different feature plus this one that will not show any feature activated.
    Background: Prepare the environment for the page Dashboard Page
        Given one instance of AppInfoPanel
        Given one instance of ServerInfoPanel
        Given one Dashboard Home Page
        Given the next authentication token
            | jwtToken                                                                                                                                                                                                                                                                                                                                                                |
            | eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6MTQyNzY2MTU3MywiYWNjb3VudE5hbWUiOiJBZGFtIEFudGlub28iLCJpc3MiOiJOZW9Db20uSW5maW5pdHkuQmFja2VuZCIsInVuaXF1ZUlkIjoidHJhbnF1aWxpdHkvOTIwMDIwNjciLCJwaWxvdElkIjo5MjAwMjA2N30.6JgBvtHyhvD8aY8-I4075tb433mYMpn9sNeYCkIO28LbhqVR4CZ-x1t_sk4IOLLtzSN07bF4c7ZceWw_ta4Brw |
        Given a valid credential with the next data
            | uniqueId             | accountId | accountName  | corporationId |
            | tranquility/92002067 | 92002067  | Adam Antinoo | 1427661573    |

    @NIF02 @NIF02.01
    Scenario: [NIF02.01]-Check that the Dashboard Home Page has the correct header panels.
        When the page is activated with the request id "DASHBOARD-HOME-SUCCESS"
        Then there is a "appinfo-panel" with the next fields
            | app-name        | app-version | app-copyright                      |
            | NEOCOM.INFINITY | 0.16.2 dev  | © 2019,2020 Dimensinfin Industries |
        And there is a "serverinfo-panel" with the next fields
            | server-name | server-status | server-capsuleers | server-laststart         |
            | Tranquility | ONLINE        | 16,523            | 2019-10-23T11:02:41.000Z |

    @NIF02 @NIF02.02
    Scenario: [NIF02.02]-The corporation data for the logged character is shown below the header panel.
        When the page is activated with the request id "DASHBOARD-HOME-SUCCESS"
        Then there is a "corporation-public-data-panel" with corporation contents
            | corporation-header | corporation-ceo-header | alliance-header |
            | CORPORATION        | CORPORATION CEO        | ALLIANCE        |
# And there is a "corporation-render" with variant "-HEADER-" with the next fields
#     | corporation-name | corporation-id | corporation-ticker | corporation-members-count |
#     | Planet-Express   | [#1427661573]  | [PLAM]             | 8 MEMBERS                 |
# And there is a "corporationceo-panel"
# And there is a "alliance-panel"
# And there is a "pilotpublicdata-panel"
# And there is a "pilotprivatedata-panel"
