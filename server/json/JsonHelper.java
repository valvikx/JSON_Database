package server.json;

import com.google.gson.*;
import server.model.Model;
import server.model.Response;

public class JsonHelper {

    private final Gson gson = new Gson();

//    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Model toModel(String json) {

        return gson.fromJson(json, Model.class);

    }

    public String toJson(Response response) {

        return gson.toJson(response);

    }

}
