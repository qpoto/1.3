package ru.qpoto.repository.impl;

import ru.qpoto.model.Writer;
import ru.qpoto.repository.WriterRepository;

import java.util.List;

public class GsonWriterRepositoryImpl implements WriterRepository {
    @Override
    public Writer findById(Long id) {
        return null;
    }

    @Override
    public List<Writer> findAll() {
        return List.of();
    }

    @Override
    public void save(Writer entity) {

    }

    @Override
    public void update(Writer entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
