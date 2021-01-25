package server.model;

import com.google.gson.JsonElement;

public class Model {

    private final String type;

    private final JsonElement key;

    private final JsonElement value;

    public Model(String type, JsonElement key, JsonElement value) {

        this.type = type;

        this.key = key;

        this.value = value;

    }

    public String getType() {

        return type;

    }

    public JsonElement getKey() {

        return key;

    }

    public JsonElement getValue() {

        return value;

    }

}
