Feature: Stores Functionality

  Scenario: Verify user should able to access stores information
    When    I sends GET request to the Stores Endpoints
    Then    I must get valid response with status code 200

  Scenario: Create a new store and verify if the store is added
    When  I create a new store by providing required informtion
    Then  I verify that if store is created with name

  Scenario: Update the store information and verify if store is updated
    When  I update store information with name and address
    Then  I verify if the store information is updated

  Scenario: I delete the store and verify store is deleted successfully
    When  I delete newly created store with id
    Then  I verify if the store is deleted
