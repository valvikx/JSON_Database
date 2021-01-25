package server.dao;

import com.google.gson.JsonElement;
import server.exception.ServerException;

public interface IJsonDao {

    JsonElement get(JsonElement key) throws ServerException;

    JsonElement delete(JsonElement key) throws ServerException;

    void set(JsonElement key, JsonElement value) throws ServerException;

}
