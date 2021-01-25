package server.command.impl;

import com.google.gson.JsonElement;
import server.dao.IJsonDao;
import server.exception.ServerException;
import server.model.Model;
import server.model.Response;

import java.util.concurrent.locks.Lock;

import static server.message.Messages.*;

public class GetCommand extends AbstractCommand {

    public GetCommand(IJsonDao dao, Lock lock) {

        super(dao, lock);

    }

    @Override
    public Response execute(Model model) throws ServerException {

        JsonElement element;

        lock.lock();

        try {

            element = dao.get(model.getKey());

        } finally {

            lock.unlock();

        }

        if (element != null) {

            return new Response(OK, element);

        }

        return new Response(ERROR, NO_SUCH_KEY);

    }

}
