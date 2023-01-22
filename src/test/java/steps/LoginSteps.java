package steps;

import com.application.servicecallers.LoginServiceCaller;
import com.application.utilities.ScenarioContext;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

public class LoginSteps {

    private final LoginServiceCaller loginServiceCaller;
    private final ScenarioContext scenarioContext;

    public LoginSteps(LoginServiceCaller loginServiceCaller, ScenarioContext scenarioContext) {
        this.loginServiceCaller = loginServiceCaller;
        this.scenarioContext = scenarioContext;
    }

    @When("^I get for a token")
    public void getForToken() {
        Response response = loginServiceCaller.getToLoginService();
        scenarioContext.setResponse(response);
    }
}
