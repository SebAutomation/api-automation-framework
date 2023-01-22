package com.application.servicecallers;

import com.application.utilities.TestUtilities;
import io.restassured.response.Response;

import java.io.IOException;

import static com.application.utilities.TestUtilities.BASE_PATH;

public class LoginServiceCaller {

    private final TestUtilities testUtilities;
    private static final String LOGIN_PATH = "v1/login";

    public LoginServiceCaller(TestUtilities testUtilities) {
        this.testUtilities = testUtilities;
    }

    public Response getToLoginService() {
        return testUtilities.makeGetWithNoHeaders(BASE_PATH + LOGIN_PATH);
    }

}
