package com.application.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.ConfigurationService;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

import static config.ConfigurationService.GLOBAL_CONFIG_PATH;
import static io.restassured.RestAssured.given;

public class TestUtilities {

    private static final ConfigurationService configurationService = new ConfigurationService();
    private final ScenarioContext scenarioContext;

    public TestUtilities(final ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    public static final String BASE_PATH = configurationService.getProperty(GLOBAL_CONFIG_PATH, "appBasePath");
    private static final String APPLICATION_JSON = "application/json";
    private static final String TEST_DATA_PATH = "./src/test/resources/TestData/";
    public static final String AUTHORIZATION = "Authorization";

    public static final String BEARER = "bearer ";

    public Response makePost(Object body, String url) throws IOException {
        return given()
                .log()
                .all()
                .headers(scenarioContext.getHeadersMap())
                .contentType(APPLICATION_JSON)
                .body(generatePayload(body))
                .when()
                .post(url)
                .then()
                .extract()
                .response();

    }

    public Response makePostWithNoHeaders(Object body, String url) throws IOException {
        return given()
                .log()
                .all()
                .contentType(APPLICATION_JSON)
                .body(generatePayload(body))
                .when()
                .post(url)
                .then()
                .extract()
                .response();

    }

    public Response makePostAnyBody(Object body, String url) {
        return given()
                .log()
                .all()
                .headers(scenarioContext.getHeadersMap())
                .contentType(APPLICATION_JSON)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();

    }

    public Response makePostAnyBodyWithNoHeaders(Object body, String url) {
        return given()
                .log()
                .all()
                .contentType(APPLICATION_JSON)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();

    }

    public Response makeGet(String url) {
        return given()
                .log()
                .all()
                .headers(scenarioContext.getHeadersMap())
                .contentType(APPLICATION_JSON)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public Response makeGetWithNoHeaders(String url) {
        return given()
                .log()
                .all()
                .contentType(APPLICATION_JSON)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public Response makeGetWithBearerToken(String bearerToken, String url) {
        return given()
                .log()
                .all()
                .headers(AUTHORIZATION, BEARER + bearerToken)
                .contentType(APPLICATION_JSON)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public Response makePut(Object body, String url) throws IOException {
        return given()
                .log()
                .all()
                .headers(scenarioContext.getHeadersMap())
                .contentType(APPLICATION_JSON)
                .body(generatePayload(body))
                .when()
                .put(url)
                .then()
                .extract()
                .response();
    }

    public Response makePutAnyBody(Object body, String url) {
        return given()
                .log()
                .all()
                .headers(scenarioContext.getHeadersMap())
                .contentType(APPLICATION_JSON)
                .body(body)
                .when()
                .put(url)
                .then()
                .extract()
                .response();
    }

    public Response makeDelete(String url) {
        return given()
                .log()
                .all()
                .headers(scenarioContext.getHeadersMap())
                .contentType(APPLICATION_JSON)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
    }

    public Response makeDeleteWithNoHeaders(String url) {
        return given()
                .log()
                .all()
                .contentType(APPLICATION_JSON)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
    }

    private String generatePayload(Object payload) throws IOException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(payload);
    }

    public static String getJsonPathAsString(Response response, String key) {
        return response.getBody().jsonPath().get(key).toString();
    }

    public static String getXmlPathAsString(Response response, String key) {
        return response.getBody().xmlPath().get(key + ".text()").toString();
    }

    public static String readFile(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(TEST_DATA_PATH + fileName), StandardCharsets.UTF_8);
        return String.join(System.lineSeparator(), lines);
    }

    public final static Pattern UUID_REGEX_PETTERN = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    public static boolean isValidUUID(String str) {
        if (str == null){
            return false;
        }
        return UUID_REGEX_PETTERN.matcher(str).matches();
    }
}
