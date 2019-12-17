@NIF02 @DashboardHome
Feature: [NIF02]-Display the character dashboard page.

    This page is where to show the Corporation data along with the Pilot data. Also there is a toolbar where to select the feature to work with. There are a set of dashboard pages, each one for a different feature plus this one that will not show any feature activated.
    Background: Prepare the environment for the page Dashboard Page
        Given one instance of AppInfoPanel
        Given one Dashboard Home Page
        Given the next authentication token
            | jwtToken                                                                                                                                                                                                                                                                                                                                                                |
            | eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6MTQyNzY2MTU3MywiYWNjb3VudE5hbWUiOiJBZGFtIEFudGlub28iLCJpc3MiOiJOZW9Db20uSW5maW5pdHkuQmFja2VuZCIsInVuaXF1ZUlkIjoidHJhbnF1aWxpdHkvOTIwMDIwNjciLCJwaWxvdElkIjo5MjAwMjA2N30.6JgBvtHyhvD8aY8-I4075tb433mYMpn9sNeYCkIO28LbhqVR4CZ-x1t_sk4IOLLtzSN07bF4c7ZceWw_ta4Brw |
        Given a valid credential with the next data
            | uniqueId             | accountId | accountName  | corporationId |
            | tranquility/92002067 | 92002067  | Adam Antinoo | 1427661573    |

    @NIF02 @NIF02.01
    Scenario: [NIF02.01]-Check that the Dashboard Home Page has the correct panels. Success Corporation download.
        When the page is activated with the request id "DASHBOARD-HOME-SUCCESS"
        Then there is a "appinfo-panel" with the next fields
            | app-name        | app-version | app-copyright                      |
            | NEOCOM.INFINITY | v0.16.1     | © 2019,2020 Dimensinfin Industries |
# And there is a "serverinfo-panel"
# And there is a "corporationdata-panel"
# And there is a "corporationceo-panel"
# And there is a "alliance-panel"
# And there is a "pilotpublicdata-panel"
# And there is a "pilotprivatedata-panel"
