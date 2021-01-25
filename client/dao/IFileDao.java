package client.dao;

import client.exception.ClientException;

public interface IFileDao {

    String get() throws ClientException;

}
