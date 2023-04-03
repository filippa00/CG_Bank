Feature: Account tests

  Scenario: Getting all account
    Given I have a valid token for the role employee
    When I call the account endpoint
    Then the result is a list of accounts of size 5

  Scenario: Getting all account
    Given I have a valid token for the role customer
    When I call the account endpoint
    Then the result is a list of accounts of size 2

  Scenario: Posting account
    Given I have a valid token for the role employee
    And I have a valid account object id "2", type "SAVINGS", absoluteLimit "-600"
    When I make a post request to the account endpoint
    Then the result is a status of 201

  Scenario: Get account balance by IBAN
    Given I have a valid token for the role employee
    When I call the /account/IBAN/balance endpoint
    Then the result is a status of 200


