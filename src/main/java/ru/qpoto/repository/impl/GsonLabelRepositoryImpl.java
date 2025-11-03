package ru.qpoto.repository.impl;

import ru.qpoto.model.Label;
import ru.qpoto.repository.LabelRepository;

import java.util.List;

public class GsonLabelRepositoryImpl implements LabelRepository {
    @Override
    public Label findById(Long id) {
        return null;
    }

    @Override
    public List<Label> findAll() {
        return List.of();
    }

    @Override
    public void save(Label entity) {

    }

    @Override
    public void update(Label entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
