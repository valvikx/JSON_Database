package server.command.impl;

import server.dao.IJsonDao;
import server.exception.ServerException;
import server.model.Model;
import server.model.Response;

import java.util.concurrent.locks.Lock;

import static server.message.Messages.OK;

public class SetCommand extends AbstractCommand {

    public SetCommand(IJsonDao dao, Lock lock) {

        super(dao, lock);

    }

    @Override
    public Response execute(Model model) throws ServerException {

        lock.lock();

        try {

            dao.set(model.getKey(), model.getValue());

        } finally {

            lock.unlock();

        }

        return new Response(OK);

    }

}
