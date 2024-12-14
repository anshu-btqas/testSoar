package cucumber.options;


import io.cucumber.core.backend.StepDefinition;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

    @RunWith(Cucumber.class)
    @CucumberOptions(features="src/test/java/Features",plugin="json:target/jsonReports/cucumber-report.json", glue={"StepDefinition"})
    public class TestRunner {
    }



