package ru.qpoto;

import ru.qpoto.controller.AppController;
import ru.qpoto.repository.LabelRepository;
import ru.qpoto.repository.PostRepository;
import ru.qpoto.repository.WriterRepository;
import ru.qpoto.repository.impl.GsonLabelRepositoryImpl;
import ru.qpoto.repository.impl.GsonPostRepositoryImpl;
import ru.qpoto.repository.impl.GsonWriterRepositoryImpl;
import ru.qpoto.view.LabelView;
import ru.qpoto.view.PostView;
import ru.qpoto.view.WriterView;

public class Main {
    public static void main(String[] args) {
        WriterView writerView = new WriterView();
        LabelView labelView = new LabelView();
        PostView postView = new PostView();
        WriterRepository gsonWriterRepository = new GsonWriterRepositoryImpl();
        LabelRepository gsonLabelRepository = new GsonLabelRepositoryImpl();
        PostRepository gsonPostRepository = new GsonPostRepositoryImpl();
        AppController controller = new AppController(writerView, labelView, postView, gsonWriterRepository, gsonLabelRepository, gsonPostRepository);

        controller.run();
    }
}
