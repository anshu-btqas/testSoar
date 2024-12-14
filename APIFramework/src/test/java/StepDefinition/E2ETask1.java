package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import StepDefinition.Hooks;

import static StepDefinition.Hooks.driver;

public class E2ETask1 {


    @Given("^Open OWASP juice app$")
    public void open_OWASP_juice_app() throws Exception {
        driver.get("https://juice-shop.herokuapp.com/#/");
        Thread.sleep(5000);
    }

    @Then("^Dismiss the welcome pop-up message$")
    public void dismiss_the_welcome_popup_message() throws Exception {
        driver.findElement(By.cssSelector("button[class*='mat-focus-indicator close-dialog']")).click();
        driver.findElement(By.cssSelector("a[class*='cc-btn cc-dismiss']")).click();
        Thread.sleep(3000);
    }

    @Then("^scroll down to the end of the page$")
    public void scroll_down_to_the_end_of_the_page() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        waitForPageToLoad();
        js.executeScript("document.body.style.overflow = 'visible';");
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        //Object result = js.executeScript("return document.body.scrollHeight;");
        //  System.out.println("Page Height: " + result);


        // js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(10000);
    }

    public static void waitForPageToLoad() {
        new WebDriverWait(driver, 10).until(
                (ExpectedCondition<Boolean>) wd ->
                        ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
        );
    }

    @Then("^Change items per page to maximum$")
    public void change_items_per_page_to_maximum() throws Exception {
        driver.findElement(By.cssSelector("div[class*='mat-select-value']")).click();
        List<WebElement> page = driver.findElements(By.cssSelector("span[class*='mat-option-text']"));
        int size = page.size();
        page.get(size - 1).click(); // Click on Items per page maximum
        // Thread.sleep(5000);
    }

    @Then("^validate home page displays all items$")
    public void validate_home_page_displays_all_items() {
        String TotalpagesText = driver.findElement(By.cssSelector("div[class*='mat-paginator-range-label']")).getText();
        String[] words = TotalpagesText.split("\\s+");
        String totalPages = words[words.length - 1];
        System.out.println("totalPages" + totalPages);
        List<WebElement> actualPages = driver.findElements(By.cssSelector("div[class*='mat-grid-tile-content']"));
        String ActualTotalContent = String.valueOf(actualPages.size());
        System.out.println("ActualTotalContent" + ActualTotalContent);

        Assert.assertEquals("Error: Not showing all content", totalPages, ActualTotalContent);
    }

    @Then("^click on first product Apple Juice$")
    public void click_on_first_product_Apple_Juice() {
        driver.findElement(By.xpath("//div[@class='item-name' and text()=' Apple Juice (1000ml) ']")).click();
    }

    @Then("^validate pop-up appeared and product image exist$")
    public void validate_popup_appeared_and_product_image_exist() {
        driver.findElement(By.cssSelector("mat-dialog-container[role='dialog']")).isDisplayed();
        driver.findElement(By.xpath("//img[contains(@src, 'apple_juice.jpg')]")).isDisplayed();
    }

    @Then("^click on Review$")
    public void click_on_Review() throws Exception {
        driver.findElement(By.cssSelector("mat-expansion-panel-header[role='button']")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("button[aria-label='Close Dialog']")).click();
    }

    @Given("^Navigate to the registration page$")
    public void navigate_to_registration_page() {
        driver.get("https://juice-shop.herokuapp.com/#/register");
    }

    @Then("^Assert validation messages for empty fields$")
    public void assert_validation_messages_for_empty_fields() throws Exception {
        driver.findElement(By.id("passwordControl")).click();
        driver.findElement(By.id("emailControl")).click();
        driver.findElement(By.id("passwordControl")).click();
        driver.findElement(By.id("repeatPasswordControl")).click();
        //driver.findElement(By.cssSelector("mat-select[role='combobox']")).click();
        // Thread.sleep(3000);
        // driver.findElement(By.xpath("//span[text()='Mother's maiden name?']")).click();
        driver.findElement(By.id("securityAnswerControl")).click();
        List<WebElement> validationMessages = driver.findElements(By.cssSelector("mat-error"));
        Assert.assertFalse("Validation messages not displayed for empty fields.", validationMessages.isEmpty());
    }

    @Then("^Fill registration form with valid data$")
    public void fill_registration_form_with_valid_data() throws Exception {
        // Self-generated valid data
        driver.findElement(By.id("emailControl")).sendKeys("testuser@example.com");
        driver.findElement(By.id("passwordControl")).sendKeys("ValidPassword1!");
        driver.findElement(By.id("repeatPasswordControl")).sendKeys("ValidPassword1!");
        driver.findElement(By.cssSelector("mat-select[role='combobox']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(text(), \"Mother's maiden name\")]")));
        option.click();
        driver.findElement(By.id("securityAnswerControl")).sendKeys("TestAnswer");
    }

    @Then("^Click show password advice$")
    public void click_show_password_advice() {
        driver.findElement(By.cssSelector(".mat-slide-toggle-thumb")).click();
        WebElement passwordAdvice = driver.findElement(By.cssSelector("mat-hint"));
        Assert.assertTrue("Password advice not visible.", passwordAdvice.isDisplayed());
    }

    @Then("^Register the user and validate successful registration$")
    public void register_and_validate_success() {
        driver.findElement(By.id("registerButton")).click();
        WebElement successMessage = new WebDriverWait(driver, 10)
                .until(driver -> driver.findElement(By.cssSelector(".mat-snack-bar-container")));
        Assert.assertTrue("Registration success message not displayed.", successMessage.isDisplayed());
        Assert.assertTrue("Registration success message is incorrect.",
                successMessage.getText().contains("Registration completed successfully. You can now log in."));
    }

    @Then("^Login using the registered credentials$")
    public void login_with_registered_credentials() {
        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
        driver.findElement(By.id("password")).sendKeys("ValidPassword1!");
        driver.findElement(By.id("loginButton")).click();
        WebElement accountIcon = new WebDriverWait(driver, 10)
                .until(driver -> driver.findElement(By.id("navbarAccount")));
        Assert.assertTrue("Login failed or user is not redirected.", accountIcon.isDisplayed());
    }

    @Given("^Navigate to the Login page$")
    public void navigate_to_the_Login_page() {
        driver.get("https://juice-shop.herokuapp.com/#/login");
    }
    @Then("^Login with the registered credentials$")
    public void login_using_registered_credentials() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.findElement(By.id("email")).sendKeys("testuser@example.com");
        driver.findElement(By.id("password")).sendKeys("ValidPassword1!");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton")));
        loginButton.click();
        WebElement accountIcon = new WebDriverWait(driver, 10)
                .until(driver -> driver.findElement(By.id("navbarAccount")));
        Assert.assertTrue("Login failed.", accountIcon.isDisplayed());
    }

    @Then("^Add five different products to the basket$")
    public void add_five_different_products_to_basket() throws Exception {
        List<WebElement> products = driver.findElements(By.cssSelector(".mat-card"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        int addedProducts = 0;

        for (WebElement product : products) {
            // Check if the product is sold out
            List<WebElement> soldOutLabels = product.findElements(By.cssSelector("div[class*='ribbon ribbon-top-left ribbon-sold ng-star-inserted']"));
            if (!soldOutLabels.isEmpty() && soldOutLabels.get(0).getText().equalsIgnoreCase("sold out")) {
                System.out.println("Skipping sold-out product.");
                continue;
            }

            // Click the "Add to Basket" button
            try {
                WebElement addToBasketButton = product.findElement(By.cssSelector("button[aria-label='Add to Basket']"));
                wait.until(ExpectedConditions.elementToBeClickable(addToBasketButton));
                addToBasketButton.click();

                // Wait for the success popup
                WebElement successPopup = new WebDriverWait(driver, 5)
                        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mat-snack-bar-container")));
                Assert.assertTrue("Success popup not displayed.", successPopup.isDisplayed());
                Thread.sleep(1000);
                addedProducts++;
                if (addedProducts == 5) {
                    break; // Stop once 5 products are added
                }
            } catch (Exception e) {
                System.out.println("Failed to add product to basket: " + e.getMessage());
            }
        }

        Assert.assertEquals("Not all 5 products could be added to the basket.", 5, addedProducts);
    }


    @Then("^Assert cart number is updated to five and success popup appears for each product$")
    public void assert_cart_number_updated() {
        WebElement cartNumber = driver.findElement(By.cssSelector(".fa-layers-counter"));
        Assert.assertEquals("Cart number is incorrect.", "5", cartNumber.getText());
    }

    @Then("^Navigate to the basket$")
    public void navigate_to_basket() {
        driver.findElement(By.cssSelector("button[routerlink='/basket']")).click();
        WebElement basket = new WebDriverWait(driver, 5)
                .until(driver -> driver.findElement(By.cssSelector(".mat-card")));
        Assert.assertTrue("Basket page not loaded.", basket.isDisplayed());
    }
static String TotalPriceBeforeIncrease;

    @Then("^Increase the quantity of one product in the basket$")
    public void increase_product_quantity_in_basket() {
        WebElement TotalPrice = driver.findElement(By.cssSelector("div[id='price']"));
        TotalPriceBeforeIncrease = TotalPrice.getText();
        System.out.println("TotalPriceBeforeIncrease"+TotalPriceBeforeIncrease);
        WebElement increaseButton = driver.findElement(By.cssSelector("svg[data-icon=plus-square]"));
        increaseButton.click();
    }

    @Then("^Assert total price is updated correctly$")
    public void assert_total_price_updated() {
       /* List<WebElement> prices = driver.findElements(By.cssSelector(".price"));
        double totalPrice = prices.stream().mapToDouble(price -> Double.parseDouble(price.getText().replace("¤", ""))).sum();
        WebElement total = driver.findElement(By.cssSelector(".total-price"));
        double displayedTotal = Double.parseDouble(total.getText().replace("¤", ""));
        Assert.assertEquals("Total price is incorrect.", totalPrice, displayedTotal, 0.01);*/
        WebElement TotalPrice = driver.findElement(By.cssSelector("div[id='price']"));
        String AfterIncreasePrice = TotalPrice.getText();
        Assert.assertNotEquals("Total Price not updated Correctly", TotalPriceBeforeIncrease, AfterIncreasePrice);

    }

    @Then("^Delete one product from the basket$")
    public void delete_product_from_basket() {
        driver.findElement(By.cssSelector("svg[data-icon='trash-alt']")).click();
    }

    @Then("^Assert total price is updated correctly after deletion$")
    public void assert_total_price_is_updated_correctly_after_deletion(){
        WebElement TotalPrice = driver.findElement(By.cssSelector("div[id='price']"));
        String AfterDeletionPrice = TotalPrice.getText();
        Assert.assertNotEquals("Total Price not updated Correctly", TotalPriceBeforeIncrease, AfterDeletionPrice);
    }

    @Then("^Click on checkout$")
    public void click_on_checkout() {
        driver.findElement(By.id("checkoutButton")).click();
    }

    @Then("^Add address information and select delivery method$")
    public void add_address_and_select_delivery_method() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Click on "Add New Address" button
        WebElement addAddressButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[routerlink='/address/create']")));
        addAddressButton.click();

        // Fill in the address form
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-input-1"))).sendKeys("Test Country"); // Country
        driver.findElement(By.id("mat-input-2")).sendKeys("testUser"); // Name
        driver.findElement(By.id("mat-input-3")).sendKeys("1234567890"); // Mobile Number
        driver.findElement(By.id("mat-input-4")).sendKeys("12345"); // ZIP Code
        driver.findElement(By.id("address")).sendKeys("123 Test Street, Suite 100"); // Address
        driver.findElement(By.id("mat-input-6")).sendKeys("Test City"); // City
        driver.findElement(By.id("mat-input-7")).sendKeys("Test State"); // State

        // Click the "Submit" button
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submitButton")));
        submitButton.click();

      /*  // Select delivery method
        WebElement deliveryMethodButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("deliveryMethod")));
        deliveryMethodButton.click();

        // Select "Fast Delivery" option
        WebElement fastDeliveryOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Fast Delivery']")));
        fastDeliveryOption.click();*/

        WebElement DeliveryOption  = driver.findElement(By.cssSelector("mat-radio-43[id='mat-radio-43']"));
        DeliveryOption.click();
        driver.findElement(By.cssSelector("button[aria-label='Proceed to delivery method selection']")).click();
    }



    @Then("^Assert wallet has no money$")
    public void assert_wallet_has_no_money() {
        WebElement walletMessage = driver.findElement(By.xpath("//button[@disabled='true' and @id='submitButton']"));
        WebElement balance = driver.findElement(By.cssSelector("span[class='confirmation card-title']"));
        String bal = balance.getText();
        Assert.assertTrue("Wallet should be empty.", bal.equals("0.00"));
        Assert.assertTrue("Wallet should be empty.", walletMessage.isDisplayed());
    }

    @Then("^Add random card information and continue purchase$")
    public void add_random_card_info_and_continue() {
        driver.findElement(By.id("addCard")).click();
        driver.findElement(By.id("cardNumber")).sendKeys("4111111111111111");
        driver.findElement(By.id("expiryMonth")).sendKeys("12");
        driver.findElement(By.id("expiryYear")).sendKeys("2025");
        driver.findElement(By.id("cardName")).sendKeys("Test User");
        driver.findElement(By.id("submitButton")).click();
        driver.findElement(By.id("continuePurchase")).click();
    }

    @Then("^Assert purchase is completed successfully$")
    public void assert_purchase_completed() {
        WebElement successMessage = new WebDriverWait(driver, 10)
                .until(driver -> driver.findElement(By.cssSelector(".purchase-completed")));
        Assert.assertTrue("Purchase was not completed successfully.", successMessage.isDisplayed());
    }


}
