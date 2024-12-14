Feature: Soar Automation Test

  @task1
  Scenario: Script for Task1
    Given Open OWASP juice app
    Then Dismiss the welcome pop-up message
    Then scroll down to the end of the page
    Then Change items per page to maximum
    Then validate home page displays all items

  @task2
  Scenario: Script for Task2
    Given Open OWASP juice app
    Then Dismiss the welcome pop-up message
    Then click on first product Apple Juice
    Then validate pop-up appeared and product image exist
    Then click on Review

  @task3
  Scenario: Script for Task3 - User Registration and Login
    Given Navigate to the registration page
    Then Dismiss the welcome pop-up message
    Then Assert validation messages for empty fields
    Then Fill registration form with valid data
    Then Click show password advice
    Then Register the user and validate successful registration
    Then Login using the registered credentials

  @task4
  Scenario: Add products to basket and complete checkout
    Given Navigate to the Login page
    Then Dismiss the welcome pop-up message
    Then Login with the registered credentials
    Then Add five different products to the basket
    Then Assert cart number is updated to five and success popup appears for each product
    Then Navigate to the basket
    Then Increase the quantity of one product in the basket
    Then Assert total price is updated correctly
    Then Delete one product from the basket
    Then Assert total price is updated correctly after deletion
    Then Click on checkout
    Then Add address information and select delivery method
    Then Assert wallet has no money
    Then Add random card information and continue purchase
    Then Assert purchase is completed successfully