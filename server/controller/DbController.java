package server.controller;

import server.command.Command;
import server.command.ICommand;
import server.command.impl.DeleteCommand;
import server.command.impl.ExitCommand;
import server.command.impl.GetCommand;
import server.command.impl.SetCommand;
import server.dao.IJsonDao;
import server.dao.impl.JsonDao;
import server.exception.ServerException;
import server.model.Model;
import server.model.Response;

import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import static server.message.Messages.*;

public class DbController {

//    private static final String DB_PATH = "JSON Database/task/src/server/data/db.json";

    private static final String DB_PATH = "./src/server/data/db.json";

    private final Map<Command, ICommand> commandFactory;

    public DbController(ReadWriteLock readWriteLock) {

        Lock readLock = readWriteLock.readLock();

        Lock writeLock = readWriteLock.writeLock();

        IJsonDao dao = new JsonDao(Paths.get(DB_PATH));

        commandFactory = Map.of(Command.GET, new GetCommand(dao, readLock),
                                Command.SET, new SetCommand(dao, writeLock),
                                Command.DELETE, new DeleteCommand(dao, writeLock),
                                Command.EXIT, new ExitCommand());

    }

    public Response executeRequest(Model model) {

        try {

            ICommand command =
                    commandFactory.get(Command.valueOf(model.getType().toUpperCase(Locale.ROOT)));

            return command.execute(model);


        } catch (ServerException e) {

            System.out.println(SERVER_ERROR + e.getMessage());

        }

         return new Response(ERROR, DATABASE_ACCESS_ERROR);

    }

}
