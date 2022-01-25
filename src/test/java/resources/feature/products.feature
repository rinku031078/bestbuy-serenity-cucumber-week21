Feature: Products Functionality

  Scenario: Verify user should be able to access Products on Best Buy application
    When   User sends GET request to the Products Endpoints
    Then   User must get back a valid status code 200

  Scenario Outline: Create a new Product & verify if the product is added
    When I create a new product by providing the information name "<NAME>" type "<TYPE>" upc "<UPC>" price "<PRICE>"description "<DESCRIPTION>" model "<MODEL>"
    Then I verify that product is created with name "<NAME>"

    Examples:
      | NAME                 | TYPE     | UPC          | PRICE | DESCRIPTION                   | MODEL    |
      | Apple iPad Pro 512GB | HardGood | 123433429874 | 899   | Apple iPad Pro 512GB - Silver | iPad Pro |

  Scenario Outline:Updating the product created and verify it is updated with status 200
    When I update the product with name "<NAME>" type "<TYPE>" upc "<UPC>" price "<PRICE>"description "<DESCRIPTION>" model "<MODEL>"
    Then I verify that the information with name "<NAME>" is updated in the product
    Examples:
      | NAME                | TYPE     | UPC          | PRICE | DESCRIPTION           | MODEL        |
      | Apple Home Pod Mini | HardGood | 123433429874 | 99    | Home Pod Mini - White | Homepod mini |

  Scenario: Verify user should able to delete the product
    When  I delete the product by product id
    Then  I verify the product is deleted successfully
