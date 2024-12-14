package StepDefinition;
import Utilities.BrowserInitialization;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks {
    public static WebDriver driver;

    @Before
    public void setUp() {
        // Default browser is Chrome, can be overridden by passing -Dbrowser=<browser> in the command line
        driver = BrowserInitialization.getDriver(System.getProperty("browser", "chrome"));
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        BrowserInitialization.closeDriver();
    }
}
