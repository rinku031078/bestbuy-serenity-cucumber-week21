Feature: Services Functionality

  Scenario: Verify user should able to access stores information
    When    I sends GET request to the Services Endpoints
    Then    I verify that valid response with status code 200

  Scenario: Create a new Service and verify if the Service is added
    When  I create a new Service by providing required informtion
    Then  I verify that if Service is created with name

  Scenario: Update the Service information and verify if Service is updated
    When  I update Service information with name
    Then  I verify if the Service information is updated

  Scenario: I delete the Service and verify Service is deleted successfully
    When  I delete newly created Service with id
    Then  I verify if the Service is deleted
