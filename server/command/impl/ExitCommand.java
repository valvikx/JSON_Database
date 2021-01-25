package server.command.impl;

import server.command.ICommand;
import server.exception.ServerException;
import server.model.Model;
import server.model.Response;

import static server.message.Messages.OK;

public class ExitCommand implements ICommand {

    @Override
    public Response execute(Model model) throws ServerException {

        return new Response(OK);

    }

}
