package client.json;

import client.model.Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class JsonHelper {

    private final Gson gson = new Gson();

//    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String toJson(Model model) {

        return gson.toJson(model);

    }

}
