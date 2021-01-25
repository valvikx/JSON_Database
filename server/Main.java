package server;

import server.handler.Handler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static server.message.Messages.SERVER_ERROR;

public class Main {

    private static final String BINDING_ADDRESS = "127.0.0.1";

    private static final int PORT = 34522;

    private static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private final ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);

    private final ReadWriteLock reentrantLock = new ReentrantReadWriteLock();

    public static void main(String[] args) {

        new Main().runServer();

    }

    private void runServer() {

        System.out.println("Server started!");

        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(BINDING_ADDRESS))) {

            while (true) {

                Future<String> type = executor.submit(new Handler(server.accept(), reentrantLock));

                if ("exit".equals(type.get())) {

                    shutdownAndAwaitTermination();

                    break;

                }

            }

        } catch (IOException e) {

            System.out.println(SERVER_ERROR + e.getMessage());

            executor.shutdown();

        } catch (InterruptedException e) {

            shutdownAndAwaitTermination();

            Thread.currentThread().interrupt();

        } catch (ExecutionException e) {

            System.out.println(SERVER_ERROR + e.getMessage());

            shutdownAndAwaitTermination();

        }

    }

    private void shutdownAndAwaitTermination() {

        executor.shutdown();

        try {

            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {

                executor.shutdownNow();

            }

        } catch (InterruptedException e) {

            executor.shutdownNow();

            Thread.currentThread().interrupt();

        }
    }

}
