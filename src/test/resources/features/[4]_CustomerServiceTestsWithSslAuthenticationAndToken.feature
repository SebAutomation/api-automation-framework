@tagName
Feature: Customer Service tests
  
  Background: 
    Given I call to the service for token with "cert" certificate
    And I receive 200 status code
    And I save response token

  Scenario: [1] Create customer with following data
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

  Scenario: [2] Create customer with following body
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
