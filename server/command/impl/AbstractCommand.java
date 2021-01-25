package server.command.impl;

import server.command.ICommand;
import server.dao.IJsonDao;

import java.util.concurrent.locks.Lock;

public abstract class AbstractCommand implements ICommand {

    protected IJsonDao dao;

    protected final Lock lock;

    public AbstractCommand(IJsonDao dao, Lock lock) {

        this.dao = dao;

        this.lock = lock;
    }

}
