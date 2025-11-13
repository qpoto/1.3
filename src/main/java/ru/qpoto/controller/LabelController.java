package ru.qpoto.controller;

import ru.qpoto.model.Label;
import ru.qpoto.model.Post;
import ru.qpoto.model.Writer;
import ru.qpoto.repository.LabelRepository;
import ru.qpoto.repository.PostRepository;
import ru.qpoto.repository.WriterRepository;
import ru.qpoto.repository.impl.GsonLabelRepositoryImpl;
import ru.qpoto.repository.impl.GsonPostRepositoryImpl;
import ru.qpoto.repository.impl.GsonWriterRepositoryImpl;

import java.util.List;

public class LabelController {
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();
    private final PostRepository postRepository = new GsonPostRepositoryImpl();
    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();

    public List<Label> findAll() {
        return labelRepository.findAll();
    }

    public void save(Label label) {
        labelRepository.save(label);
    }

    public void delete(Long id) {
        labelRepository.delete(id);
        postRepository.deleteLabel(id);
        writerRepository.deleteLabel(id);
    }

    public void update(Label label) {
        labelRepository.update(label);
    }

    public Label findById(Long id) {
        return labelRepository.findById(id);
    }
}
