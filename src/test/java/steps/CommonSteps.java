package steps;

import com.application.servicecallers.LoginServiceCaller;
import com.application.utilities.ScenarioContext;
import config.ConfigurationService;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.application.utilities.TestUtilities.*;
import static config.ConfigurationService.GLOBAL_CONFIG_PATH;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

public class CommonSteps {

    private final ConfigurationService configurationService;
    private final LoginServiceCaller loginServiceCaller;
    private final ScenarioContext scenarioContext;
    private final Map<String, String> headersMap = new HashMap<>();

    public CommonSteps(final ConfigurationService configurationService, final LoginServiceCaller loginServiceCaller,
                       final ScenarioContext scenarioContext) {
        this.configurationService = configurationService;
        this.loginServiceCaller = loginServiceCaller;
        this.scenarioContext = scenarioContext;
    }

    @Given("^I call to the service for token$")
    public void callToTheServiceForToken() {
        Response response = loginServiceCaller.getToLoginService();
        scenarioContext.setResponse(response);
    }

    @Given("^I call to the service with \"([^\"]*)\" certificate$")
    public void callToTheServiceWithCertificate(String certificate) {
        configurationService.configureSsl(GLOBAL_CONFIG_PATH, certificate);
    }

    @Given("^I call to the service for token with \"([^\"]*)\" certificate$")
    public void callToTheServiceForTokenWithCertificate(String certificate) {
        configurationService.configureSsl(GLOBAL_CONFIG_PATH, certificate);
        Response response = loginServiceCaller.getToLoginService();
        scenarioContext.setResponse(response);
    }

    @Given("^I set following request headers$")
    public void setFollowingRequestHeaders(final Map<String, String> elements) {
        elements.forEach((key, value) ->
        {
            scenarioContext.setHeadersMap(elements);
        });
    }

    @Then("^the response is structured according to schema \"([^\"]*)\"$")
    public void theResponseIsStructuredAccordingToSchema(String schemaName) {
        assertThat("Expected json schema is not matched.", scenarioContext.getResponse().getBody().asString(),
                matchesJsonSchemaInClasspath("schemas/" + schemaName));
    }

    @Then("I receive (\\d+) status code$")
    public void receiveStatusCode(int statusCode) {
        assertThat("Status code is " + scenarioContext.getResponse().getStatusCode()
                        + " instead of expected " + statusCode,
                scenarioContext.getResponse().getStatusCode(), Matchers.is(equalTo(statusCode)));
    }

    @Then("^the response is equal to following content$")
    public void theResponseIsEqualToFollowingContent(List<String> content) {
        content.forEach(element ->
                assertThat("Actual response doesn't equal to expected one.",
                        scenarioContext.getResponse().asString(), Matchers.is(equalTo(element))));
    }

    @And("^the response contains following content$")
    public void theResponseContainsFollowingContent(List<String> content) {
        String actualResponse = scenarioContext.getResponse().asString();
        content.forEach(element ->
                assertThat("Actual response doesn't equal to expected one. Actual response: " + actualResponse,
                        actualResponse.contains(element)));
    }

    @And("^the response doesn't contain any of following elements$")
    public void theResponseDoesNotContainAnyOfFollowingElements(final List<String> content) {
        content.forEach(element ->
                assertFalse(
                        scenarioContext.getResponse().getBody().asString().contains(element),
                        "Actual response contains unexpected element: " + element));
    }

    @And("^I save response elements:$")
    public void saveResponseElements(final Map<String, String> elements) {
        elements.forEach((key, value) -> {
            if (getJsonPathAsString(scenarioContext.getResponse(), key) != null) {
                scenarioContext.getKeyMap().add(value, getJsonPathAsString(scenarioContext.getResponse(), key));
            } else {
                fail("Response: " + scenarioContext.getResponse().prettyPrint() + " does not have element: " + key);
            }
        });
    }

    @And("^response has following elements$")
    public void responseHasFollowingElements(final Map<String, String> elements) {
        elements.forEach((key, value) ->
        {
            assertThat(key + " doesn't have value " + value,
                    getJsonPathAsString(scenarioContext.getResponse(), key),
                    Matchers.is(valueOf(value)));
        });
    }

    @And("^I save response token$")
    public void saveResponseToken() {
        headersMap.put(AUTHORIZATION, BEARER + getJsonPathAsString(scenarioContext.getResponse(), "token"));
        scenarioContext.setHeadersMap(headersMap);
    }

}
