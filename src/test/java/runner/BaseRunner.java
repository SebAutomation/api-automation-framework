package runner;

import com.application.utilities.LogsTool;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.*;

public class BaseRunner {
    protected TestNGCucumberRunner testNGCucumberRunner;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
    }

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        LogsTool.setExtentReportsInfo();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        testNGCucumberRunner.finish();
    }
}