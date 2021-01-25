package server.command;

import server.exception.ServerException;
import server.model.Model;
import server.model.Response;

public interface ICommand {

    Response execute(Model model) throws ServerException;

}
