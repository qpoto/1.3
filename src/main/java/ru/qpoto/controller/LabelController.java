package ru.qpoto.controller;

import ru.qpoto.model.Label;
import ru.qpoto.repository.LabelRepository;
import ru.qpoto.repository.PostRepository;
import ru.qpoto.repository.WriterRepository;

import java.util.List;

public class LabelController {
    private final LabelRepository labelRepository;
    private final PostRepository postRepository;
    private final WriterRepository writerRepository;

    public LabelController(LabelRepository labelRepository, PostRepository postRepository, WriterRepository writerRepository) {
        this.labelRepository = labelRepository;
        this.postRepository = postRepository;
        this.writerRepository = writerRepository;
    }

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
