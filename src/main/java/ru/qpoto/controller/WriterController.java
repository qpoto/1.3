package ru.qpoto.controller;

import ru.qpoto.repository.WriterRepository;
import ru.qpoto.repository.impl.GsonWriterRepositoryImpl;

public class WriterController {
    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();


}
