package ru.qpoto;

import ru.qpoto.controller.AppController;
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
        GsonWriterRepositoryImpl gsonWriterRepository = new GsonWriterRepositoryImpl();
        GsonLabelRepositoryImpl gsonLabelRepository = new GsonLabelRepositoryImpl();
        GsonPostRepositoryImpl gsonPostRepository = new GsonPostRepositoryImpl();
        AppController controller = new AppController(writerView, labelView, postView, gsonWriterRepository, gsonLabelRepository, gsonPostRepository);

        controller.run();
    }
}
