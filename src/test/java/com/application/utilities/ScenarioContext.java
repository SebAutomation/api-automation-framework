package com.application.utilities;

import io.restassured.response.Response;

import java.util.Map;

public class ScenarioContext {

    private Response response;
    private KeyMap keyMap = new KeyMap();
    private Map<String, String> headersMap;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public KeyMap getKeyMap() {
        return keyMap;
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }

    public void setHeadersMap(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }
}
