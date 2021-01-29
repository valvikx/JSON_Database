package server.command.impl;

import com.google.gson.JsonElement;
import server.dao.IJsonDao;
import server.exception.ServerException;
import server.model.Model;
import server.model.Response;

import java.util.concurrent.locks.Lock;

import static server.message.Messages.*;

public class DeleteCommand extends AbstractCommand{

    public DeleteCommand(IJsonDao dao, Lock lock) {

        super(dao, lock);

    }

    @Override
    public Response execute(Model model) throws ServerException {

        JsonElement element;

        lock.lock();

        try {

            element = dao.delete(model.getKey());

        } finally {

            lock.unlock();

        }

        return element != null ? new Response(OK) : new Response(ERROR, NO_SUCH_KEY);

    }

}
