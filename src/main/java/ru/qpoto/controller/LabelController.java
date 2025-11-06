package ru.qpoto.controller;

import ru.qpoto.repository.LabelRepository;
import ru.qpoto.view.LabelView;

public class LabelController {
    private final LabelRepository labelRepository;
    private final LabelView labelView;

    public LabelController(LabelRepository labelRepository, LabelView labelView) {
        this.labelRepository = labelRepository;
        this.labelView = labelView;
    }
}
