package ru.qpoto.controller;

import ru.qpoto.repository.LabelRepository;
import ru.qpoto.repository.PostRepository;
import ru.qpoto.repository.WriterRepository;
import ru.qpoto.view.LabelView;
import ru.qpoto.view.PostView;
import ru.qpoto.view.WriterView;

public class AppController {
    private WriterView writerView;
    private LabelView labelView;
    private PostView postView;
    private WriterRepository writerRepository;
    private LabelRepository labelRepository;
    private PostRepository postRepository;

    public AppController(WriterView writerView, LabelView labelView, PostView postView, WriterRepository writerRepository, LabelRepository labelRepository, PostRepository postRepository) {
        this.writerView = writerView;
        this.labelView = labelView;
        this.postView = postView;
        this.writerRepository = writerRepository;
        this.labelRepository = labelRepository;
        this.postRepository = postRepository;
    }


    public void run(){

    }
}
