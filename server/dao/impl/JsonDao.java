package server.dao.impl;

import com.google.gson.*;
import server.dao.IJsonDao;
import server.exception.ServerException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonDao implements IJsonDao {

    private final Path path;

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private JsonElement data;

    public JsonDao(Path path) {

        this.path = path;

    }

    @Override
    public JsonElement get(JsonElement key) throws ServerException {

        data = getFromDb();

        if (data.isJsonNull()) {

            return null;

        }

        JsonObject element = getElement(key);

        String valueOfKey = getValue(key);

        return element != null && element.has(valueOfKey) ? element.get(valueOfKey) : null;

    }

    @Override
    public JsonElement delete(JsonElement key) throws ServerException {

        data = getFromDb();

        if (data.isJsonNull()) {

            return null;

        }

        JsonObject element = getElement(key);

        String valueOfKey = getValue(key);

        if (element != null) {

            JsonElement removeElement = element.has(valueOfKey) ? element.remove(valueOfKey) : null;

            if (removeElement != null) {

                saveToDb(data);

                return removeElement;

            }

        }

        return null;

    }

    @Override
    public void set(JsonElement key, JsonElement value) throws ServerException {

        data = getFromDb();

        if (data.isJsonNull()) {

            data = new JsonObject();

        }

        JsonObject element = getElementWithSetElementsIfAbsent(key);

        String valueOfKey = getValue(key);

        element.add(valueOfKey, value);

        saveToDb(data);

    }

    private JsonObject getElement(JsonElement key) {

        JsonObject currentElement = data.getAsJsonObject();

        if (key.isJsonPrimitive()) {

            return currentElement.has(key.getAsString()) ? currentElement : null;

        }

        JsonArray elements = key.getAsJsonArray();

        if (elements.size() == 1) {

            return currentElement.has(elements.get(0).getAsString()) ? currentElement : null;

        }

        for (int i = 0; i < elements.size() - 1; i++) {

            if (currentElement.has(elements.get(i).getAsString())) {

                if (currentElement.get(elements.get(i).getAsString()).isJsonPrimitive()) {

                    break;

                }

                currentElement = currentElement.getAsJsonObject(elements.get(i).getAsString());

            } else {

                return null;

            }

        }

        return currentElement;

    }

    private JsonObject getElementWithSetElementsIfAbsent(JsonElement key) {

        JsonObject currentElement = data.getAsJsonObject();

        if (key.isJsonPrimitive()) {

            return currentElement;

        }

        JsonArray elements = key.getAsJsonArray();

        if (elements.size() == 1) {

            return currentElement;

        }

        for (int i = 0; i < elements.size() - 1; i++) {

            if (!currentElement.has(elements.get(i).getAsString())) {

                currentElement.add(elements.get(i).getAsString(), new JsonObject());

            }

            currentElement = currentElement.getAsJsonObject(elements.get(i).getAsString());

        }

        return currentElement;

    }

    private JsonElement getFromDb() throws ServerException {

        try (BufferedReader reader = Files.newBufferedReader(path)) {

            return JsonParser.parseReader(reader);

        } catch (IOException e) {

            throw new ServerException(e.getMessage());

        }

    }

    private void saveToDb(JsonElement data) throws ServerException {

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {

            gson.toJson(data, writer);

        } catch (IOException e) {

            throw new ServerException(e.getMessage());

        }

    }

    private String getValue(JsonElement key) {

        if (key.isJsonPrimitive()) {

            return key.getAsString();

        }

        JsonArray keys = key.getAsJsonArray();

        return keys.get(keys.size() - 1).getAsString();

    }

}
