@tagName
Feature: Customer Service tests

  Scenario: [1] Create customer with following data
    When I create a customer with following data
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

  Scenario: [2] Create customer with following body
    When I create a customer with following body "customerData.json"
    Then I save response elements:
      | customId | ${customId} |
    And I receive 201 status code
    And the response is structured according to schema "customerService.json"
    And response has following elements
      | id      | 532111 |
      | name    | Mark   |
      | surname | Web    |
      | age     | 32     |
