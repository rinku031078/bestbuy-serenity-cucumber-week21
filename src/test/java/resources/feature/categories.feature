Feature: Categories Functionality

  Scenario: Verify user should able to access categories information
    When  I send GET request to the Categories Endpoint
    Then  I verify response status code is 200

  Scenario: Create new category and verify if the category is added
    When  I create a new category by providing information
    Then  I verify that the category with name is created

  Scenario: Update the category information and verify if category is updated
    When  I update category information with name
    Then  I verify if the category information is updated

  Scenario: I delete the store and verify category is deleted successfully
    When  I delete newly created category with id
    Then  I verify if the category is deleted