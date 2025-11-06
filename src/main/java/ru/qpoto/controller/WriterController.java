package ru.qpoto.controller;

import ru.qpoto.repository.WriterRepository;
import ru.qpoto.view.WriterView;

public class WriterController {
    private final WriterRepository writerRepository;
    private final WriterView writerView;


    public WriterController(WriterRepository writerRepository, WriterView writerView) {
        this.writerRepository = writerRepository;
        this.writerView = writerView;
    }
}
