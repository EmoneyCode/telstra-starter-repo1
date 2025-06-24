Feature: Is the Sim Card activation successful

    Scenario: This should be the passing
        Given the ICCID "1255789453849037777" with the email "success@gmail.com"
        When I query the simcard with ID 1
        Then the SIM card is "active"

    Scenario: This should be the failing
        Given the ICCID "8944500102198304826" with the email "fail@gmail.com"
        When I query the simcard with ID 2
        Then the SIM card is "not active"