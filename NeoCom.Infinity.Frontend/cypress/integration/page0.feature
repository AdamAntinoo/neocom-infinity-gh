Feature: Background Section

    Scenario: Basic example #1
        Given page zero
        When search application name
        Then the name should contain "NEOCOM.INFINITY"
