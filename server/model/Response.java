package server.model;

import com.google.gson.JsonElement;

public class Response {

    private final String response;

    private JsonElement value;

    private String reason;

    public Response(String response) {

        this.response = response;

    }

    public Response(String response, String reason) {

        this.response = response;

        this.reason = reason;

    }

    public Response(String response, JsonElement value) {

        this.response = response;

        this.value = value;

    }

}
