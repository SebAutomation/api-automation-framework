package steps;

import com.application.servicecallers.CustomerServiceCaller;
import com.application.utilities.ScenarioContext;
import cucumber.api.java.After;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

public class CustomerSteps {

    private final CustomerServiceCaller customerServiceCaller;
    private final ScenarioContext scenarioContext;

    public CustomerSteps(CustomerServiceCaller customerServiceCaller, ScenarioContext scenarioContext) {
        this.customerServiceCaller = customerServiceCaller;
        this.scenarioContext = scenarioContext;
    }

    @When("^I create a customer with following data$")
    public void createACustomerWithFollowingData(final Map<String, String> elements) throws IOException {
        Response response = customerServiceCaller.createCustomerWithFollowingData(elements);
        scenarioContext.setResponse(response);
    }

    @When("^I create a customer with token and following data$")
    public void createACustomerWithTokenAndFollowingData(final Map<String, String> elements) throws IOException {
        Response response = customerServiceCaller.createCustomerWithTokenAndFollowingData(elements);
        scenarioContext.setResponse(response);
    }

    @When("^I create a customer with following body \"([^\"]*)\"$")
    public void createACustomerWithFollowingData(String body) throws IOException {
        Response response = customerServiceCaller.createCustomerWithAnyBody(body);
        scenarioContext.setResponse(response);
    }

    @When("^I create a customer with token and following body \"([^\"]*)\"$")
    public void createACustomerWithTokenAndFollowingData(String body) throws IOException {
        Response response = customerServiceCaller.createCustomerWithTokenAnyBody(body);
        scenarioContext.setResponse(response);
    }

    @When("^I get customer by \"([^\"]*)\"$")
    public void getCustomerBy(String customerId) {
        Response response = customerServiceCaller.getCustomerByCustomerId(scenarioContext.getKeyMap().apply(customerId));
        scenarioContext.setResponse(response);
    }

    @When("^I delete customer by \"([^\"]*)\"$")
    public void deleteCustomer(String customerId) {
        Response response = customerServiceCaller.deleteCustomerById(scenarioContext.getKeyMap().apply(customerId));
        scenarioContext.setResponse(response);
    }

    @After("@DeleteCustomerAfterTest")
    public void deleteCustomerAfterTest() {
        deleteCustomer(scenarioContext.getKeyMap().apply("${customerId}"));
    }
}
