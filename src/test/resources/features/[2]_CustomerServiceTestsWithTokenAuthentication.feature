@tagName @DeleteCustomerAfterTest
Feature: Customer Service tests

  Background:
    Given I call to the service for token
    And I receive 200 status code
    And I save response token

  Scenario: [1] Create and get customer with following data
    When I create a customer with token and following data
      | id      | 532111 |
      | name    | Mark   |
      | surname | Web    |
      | age     | 32     |
    Then I save response elements:
      | customId | ${customId} |
    And I receive 201 status code
    And the response is structured according to schema "customerService.json"
    And response has following elements
      | id      | 532111 |
      | name    | Mark   |
      | surname | Web    |
      | age     | 32     |

    When I get customer by "${customerId}"
    Then I receive 200 status code
    And response has following elements
      | id      | 532111 |
      | name    | Mark   |
      | surname | Web    |
      | age     | 32     |

  Scenario: [2] Create and get customer with following body
    When I create a customer with token and following body "customerData.json"
    Then I save response elements:
      | customId | ${customId} |
    And I receive 201 status code
    And the response is structured according to schema "customerService.json"
    And response has following elements
      | id      | 532111 |
      | name    | Mark   |
      | surname | Web    |
      | age     | 32     |

    When I get customer by "${customerId}"
    Then I receive 200 status code
    And response has following elements
      | id      | 532111 |
      | name    | Mark   |
      | surname | Web    |
      | age     | 32     |
