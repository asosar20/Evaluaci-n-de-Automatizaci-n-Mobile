Feature: Shopping functionality
  As a user
  I want to browse and purchase products
  So that I can buy what I need

  @android @shopping
  Scenario: Add product to cart
    Given User is logged into the application
    When he selects the first product
    And he adds the product to the cart
    Then he should see the product details