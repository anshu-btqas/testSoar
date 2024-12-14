package StepDefinition;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.example.Base;
import org.junit.Assert;
import org.openqa.selenium.*;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

import java.time.Duration;
import java.util.*;


import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;

public class wikiapp extends Base {


    @Given("^wikipedia app is launched$")
    public void wikipediaappislaunched() throws MalformedURLException {
        setup();
        System.out.println("Wikipedia app is launched.");
        driver.findElement(By.id("android:id/button1")).click();
    }

    @When("I scroll down using TouchAction")
    public void iScrollDownUsingTouchAction() {

        int startX = 550;
        int startY = 640;
        int endY = 60;

        try {

            TouchAction<?> touchAction = new TouchAction<>((PerformsTouchActions) driver);

            touchAction
                    .press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(java.time.Duration.ofSeconds(1)))
                    .moveTo(PointOption.point(startX, endY))
                    .release()
                    .perform();
        } catch (Exception e) {
            System.out.println("Scroll failed: " + e.getMessage());
        }
    }


    @When("I click on {string}")
    public void iClickOn(String section) {
        WebElement element = null;

        switch (section) {
            case "My lists":
                element = driver.findElement(By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"));
                break;
            case "History":
                element = driver.findElement(By.xpath("//android.widget.FrameLayout[@content-desc='History']"));
                break;
            case "Nearby":
                element = driver.findElement(By.xpath("//android.widget.FrameLayout[@content-desc='Nearby']"));
                break;
        }

        if (element != null) {
            element.click();
        } else {
            throw new RuntimeException("Section " + section + " not found.");
        }
    }

    @Then("I wait for {int} seconds")
    public void iWaitForSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000L);
    }

    @When("I go back to the home page")
    public void iGoBackToTheHomePage() {
        WebElement browseIcon = driver.findElement(By.xpath("//android.widget.FrameLayout[@content-desc='Explore']"));
        browseIcon.click();
    }

    @Then("I scroll up to the first topic of the app")
    public void iScrollUpToTheFirstTopicOfTheApp() {
        // Define the coordinates for the scroll gesture
        int startX = 550; // Adjust based on the center of the scrollable area
        int startY = 640; // Starting Y-coordinate for the scroll
        int endY = 1000;  // Ending Y-coordinate for the scroll (scrolling up)

        try {
            // Create a TouchAction instance
            TouchAction<?> touchAction = new TouchAction<>((PerformsTouchActions) driver);

            // Perform the scroll gesture
            touchAction
                    .press(PointOption.point(startX, startY)) // Start point of the gesture
                    .waitAction(WaitOptions.waitOptions(java.time.Duration.ofSeconds(1))) // Wait for the gesture duration
                    .moveTo(PointOption.point(startX, endY)) // End point of the gesture
                    .release() // Release the touch
                    .perform(); // Perform the action

            System.out.println("Scroll up action performed successfully.");
        } catch (Exception e) {
            System.out.println("Scroll up failed: " + e.getMessage());
        }
    }

    @When("I search for {string} in the search bar")
    public void iSearchForKeyword(String keyword) throws Exception {
            WebElement searchBar = driver.findElement(By.id("org.wikipedia.alpha:id/search_container"));
            searchBar.click();
            Thread.sleep(3000);
            WebElement searchInput = driver.findElement(By.id("org.wikipedia.alpha:id/search_src_text"));
            searchInput.sendKeys(keyword);

            System.out.println("Search term '" + keyword + "' entered successfully.");
    }

    @Then("I verify search results are displayed")
    public void iVerifySearchResultsAreDisplayed() throws Exception{

            Thread.sleep(3000);
            WebElement searchResults = driver.findElement(By.id("org.wikipedia.alpha:id/search_results_list"));
            Assert.assertTrue(searchResults.isDisplayed());

            System.out.println("Search results are displayed successfully.");
    }

    @Then("I double click on the close search button and return to the home page")
    public void iDoubleClickOnCloseSearchButton() {
        try {
            WebElement closeButton = driver.findElement(By.id("org.wikipedia.alpha:id/search_close_btn"));
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence doubleTapSequence = new Sequence(finger, 0);

            // First tap
            doubleTapSequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),
                    closeButton.getLocation().getX(), closeButton.getLocation().getY()));
            doubleTapSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            doubleTapSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Pause between taps
            doubleTapSequence.addAction(new Pause(finger, Duration.ofMillis(100)));

            // Second tap
            doubleTapSequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),
                    closeButton.getLocation().getX(), closeButton.getLocation().getY()));
            doubleTapSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            doubleTapSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Perform the double tap
            driver.perform(Arrays.asList(doubleTapSequence));

            System.out.println("Double-clicked on the close button successfully.");
        } catch (Exception e) {
            System.out.println("Double click on close button failed: " + e.getMessage());
        }
    }

    @When("I navigate to settings and disable all options")
    public void navigateToSettingsAndDisableAllOptions() {
        try {
            // Locate and click the settings icon
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("org.wikipedia.alpha:id/menu_overflow_button")));
            menu.click();
            WebElement settingsIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("org.wikipedia.alpha:id/explore_overflow_settings")));
            settingsIcon.click();
            System.out.println("Navigated to Settings.");

            // Locate all toggles or switches in the settings page
            List<WebElement> toggles = driver.findElements(By.xpath("//android.widget.Switch"));

            // Disable all toggles
            for (WebElement toggle : toggles) {
                if (toggle.getAttribute("checked").equals("true")) { // If toggle is enabled
                    toggle.click();
                    System.out.println("Disabled a toggle.");
                }
            }

            System.out.println("All options disabled.");
        } catch (Exception e) {
            System.out.println("Failed to disable settings: " + e.getMessage());
        }
    }

    @Then("I return to the home page")
    public void returnToHomePage() {
        try {
            // Press the back button to return to the home page
            driver.navigate().back();
            System.out.println("Returned to the home page.");
        } catch (Exception e) {
            System.out.println("Failed to return to home page: " + e.getMessage());
        }
    }


}




