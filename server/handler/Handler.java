package server.handler;

import server.controller.DbController;
import server.json.JsonHelper;
import server.model.Model;
import server.model.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReadWriteLock;

import static server.message.Messages.SERVER_ERROR;

public class Handler implements Callable<String> {

    private final Socket socket;

    private final DbController dbController;

    private final  JsonHelper jsonHelper = new JsonHelper();

    public Handler(Socket socket, ReadWriteLock readWriteLock) {

        this.socket = socket;

        dbController = new DbController(readWriteLock);

    }

    public String call() {

        String type = null;

        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            String json = input.readUTF();

            Model model = jsonHelper.toModel(json);

            type = model.getType();

            Response response = dbController.executeRequest(model);

            json = jsonHelper.toJson(response);

            output.writeUTF(json);

        } catch (IOException e) {

            System.out.println(SERVER_ERROR + e.getMessage());

        }

        return type;

    }

}
