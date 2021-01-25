package client;

import client.controller.JsonController;
import client.model.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    private static final String SERVER_ADDRESS = "127.0.0.1";

    private static final int PORT = 34522;

    private final JsonController jsonController = new JsonController();

    public static void main(String[] args) {

        new Main().runClient(args);

    }

    private void runClient(String[] args) {

        System.out.println("Client started!");

        try (Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output  = new DataOutputStream(socket.getOutputStream())) {

            Model model = new Model(args);

            String json = jsonController.getJson(model);

            if (json == null) {

                return;

            }

            System.out.println("Sent: " + json);

            output.writeUTF(json);

            json = input.readUTF();

            System.out.println("Received:");

            System.out.println(json);

        } catch (IOException e) {

            System.out.println("Client Error: " + e.getMessage());

        }

    }

}
