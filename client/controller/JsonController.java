package client.controller;

import client.dao.IFileDao;
import client.dao.impl.FileDao;
import client.exception.ClientException;
import client.json.JsonHelper;
import client.model.Model;
import com.beust.jcommander.JCommander;

import java.nio.file.Paths;

public class JsonController {

//    private static final String FILE_PATH = "JSON Database/task/src/client/data/";

    private static final String FILE_PATH = "./src/client/data/";

    private final JsonHelper jsonHelper = new JsonHelper();

    public String getJson(Model model) {

        JCommander.newBuilder()
                            .addObject(model)
                            .build()
                            .parse(model.getArgs());

        if (model.getIn() != null) {

            IFileDao dao = new FileDao(Paths.get(FILE_PATH + model.getIn()));

            try {

                return dao.get();

            } catch (ClientException e) {

                System.out.println("Client Error: " + e.getMessage());

            }

        } else if (model.getType() != null) {

            return jsonHelper.toJson(model);

        } else {

            System.out.println("Client Error: Invalid command arguments");

        }

        return null;

    }

}
