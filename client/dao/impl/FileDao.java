package client.dao.impl;

import client.exception.ClientException;
import client.dao.IFileDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDao implements IFileDao {

    private final Path path;

    public FileDao(Path path) {

        this.path = path;

    }

    @Override
    public String get() throws ClientException {

        try {

            return Files.readString(path);

        } catch (IOException e) {

            throw new ClientException(e.getMessage());

        }

    }

}
