package ru.qpoto.controller;

import ru.qpoto.repository.LabelRepository;
import ru.qpoto.repository.impl.GsonLabelRepositoryImpl;

public class LabelController {
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();
}
