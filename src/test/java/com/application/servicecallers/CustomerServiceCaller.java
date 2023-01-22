package com.application.servicecallers;

import com.application.dataproviders.CustomerDataProvider;
import com.application.utilities.TestUtilities;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

import static com.application.utilities.TestUtilities.BASE_PATH;
import static com.application.utilities.TestUtilities.readFile;

public class CustomerServiceCaller {

    private final TestUtilities testUtilities;
    private final CustomerDataProvider customerDataProvider;
    private static final String CUSTOMER_PATH = "v1/customers";

    public CustomerServiceCaller(final TestUtilities testUtilities, final CustomerDataProvider customerDataProvider) {
        this.testUtilities = testUtilities;
        this.customerDataProvider = customerDataProvider;
    }

    public Response createCustomerWithFollowingData(Map<String, String> customerData) throws IOException {
        return testUtilities.makePostWithNoHeaders(customerDataProvider.createCustomer(customerData), BASE_PATH + CUSTOMER_PATH);
    }

    public Response createCustomerWithTokenAndFollowingData(Map<String, String> customerData) throws IOException {
        return testUtilities.makePost(customerDataProvider.createCustomer(customerData), BASE_PATH + CUSTOMER_PATH);
    }

    public Response createCustomerWithAnyBody(String body) throws IOException {
        return testUtilities.makePostAnyBodyWithNoHeaders(readFile(body), BASE_PATH + CUSTOMER_PATH);
    }

    public Response createCustomerWithTokenAnyBody(String body) throws IOException {
        return testUtilities.makePostAnyBody(readFile(body), BASE_PATH + CUSTOMER_PATH);
    }

    public Response getCustomerByCustomerId(String customerId) {
        return testUtilities.makeGet(BASE_PATH + CUSTOMER_PATH + "/" + customerId);
    }

    public Response deleteCustomerById(String customerId) {
        return testUtilities.makeDelete(BASE_PATH + CUSTOMER_PATH + "/" + customerId);
    }

}
