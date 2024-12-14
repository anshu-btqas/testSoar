package org.example;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
public class Base {

        protected static AppiumDriver driver;

        public static void setup() throws MalformedURLException {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
           // caps.setCapability("platformVersion", "12");
            //caps.setCapability("deviceName", "Pixel_Emulator");
            caps.setCapability("appium:appPackage", "org.wikipedia.alpha");
            caps.setCapability("appium:appActivity", "org.wikipedia.main.MainActivity");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:autoAcceptAlerts",true);

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        }

        public static void teardown() {
            if (driver != null) {
                driver.quit();
            }
        }
    }


