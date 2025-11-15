package ru.qpoto;

import ru.qpoto.controller.LabelController;
import ru.qpoto.controller.PostController;
import ru.qpoto.controller.WriterController;
import ru.qpoto.repository.LabelRepository;
import ru.qpoto.repository.PostRepository;
import ru.qpoto.repository.WriterRepository;
import ru.qpoto.repository.impl.GsonLabelRepositoryImpl;
import ru.qpoto.repository.impl.GsonPostRepositoryImpl;
import ru.qpoto.repository.impl.GsonWriterRepositoryImpl;
import ru.qpoto.view.LabelView;
import ru.qpoto.view.PostView;
import ru.qpoto.view.WriterView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        WriterRepository writerRepository = new GsonWriterRepositoryImpl();
        PostRepository postRepository = new GsonPostRepositoryImpl();
        LabelRepository labelRepository = new GsonLabelRepositoryImpl();

        WriterController writerController = new WriterController(writerRepository);
        PostController postController = new PostController(postRepository, writerRepository);
        LabelController labelController = new LabelController(labelRepository, postRepository, writerRepository);

        LabelView labelView = new LabelView(labelController, scanner);
        PostView postView = new PostView(postController, labelController, labelView, scanner);
        WriterView writerView = new WriterView(writerController, postController, postView, scanner);

        new StartUI(scanner, writerView, labelView, postView).run();
    }
}
