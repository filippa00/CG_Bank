Feature: User tests

  Scenario: Getting all users
    Given I have a valid token for the role employee
    When I call the getAll user endpoint
    Then the result is a status of 200

  Scenario: Getting users with invalid token
    Given I have an invalid token
    When I call the user endpoint
    Then the result is a status of 401

  Scenario: Getting users with an expired token
    Given I have an expired token
    When I call the user endpoint
    Then the result is a status of 401

  Scenario: Posting user
    And I have a valid user object username "nina", password "1235", firstname "nina", lastname "rgrg"
    When I make a post request to the user endpoint
    Then the result is a status of 201

  Scenario: Updating user with role customer
    Given I have a valid token for the role customer
    And I have a valid user object username "user1", password "1523", dayLimit "500.00", transactionLimit "200.00"
    When I make a put request to the user endpoint
    Then the result is a status of 204

  Scenario: Getting users performed by customer
    Given I have a valid token for the role customer
    When I call the user endpoint
    Then the result is a status of 401

  Scenario: Getting users without account
    Given I have a valid token for the role employee
    When I call the user/noAccount endpoint
    Then the result is a status of 200

  Scenario: Get totalBalance of a certain account
    Given I have a valid token for the role customer
    When I call the user/userId/totalBalance endpoint
    Then the result is a status of 200



