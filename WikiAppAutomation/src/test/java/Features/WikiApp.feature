Feature: WikiApp

  Scenario: Task1
    Given wikipedia app is launched
    When I scroll down using TouchAction
    And I click on "My lists"
    Then I wait for 3 seconds
    When I click on "History"
    Then I wait for 3 seconds
    When I click on "Nearby"
    Then I wait for 3 seconds
    And I go back to the home page
    Then I scroll up to the first topic of the app


  Scenario: Task2 Search for a keyword and verify results

    When I search for "New York" in the search bar
    Then I verify search results are displayed
    And I double click on the close search button and return to the home page


  Scenario: Task 3 Disable all options in settings
    Given wikipedia app is launched
    When I navigate to settings and disable all options
    Then I return to the home page

