package ru.qpoto.controller;

import ru.qpoto.model.Writer;
import ru.qpoto.repository.WriterRepository;

import java.util.List;

public class WriterController {
    private final WriterRepository writerRepository;

    public WriterController(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public List<Writer> findAll() {
        return writerRepository.findAll();
    }

    public void save(Writer writer) {
        writerRepository.save(writer);
    }

    public void delete(Long id) {
        writerRepository.delete(id);
    }

    public void update(Writer writer) {
        writerRepository.update(writer);
    }

    public Writer findById(Long id) {
        return writerRepository.findById(id);
    }

}
