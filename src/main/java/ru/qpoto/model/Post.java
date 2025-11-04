package ru.qpoto.model;

import java.util.List;

public class Post {
    private Long id;
    private String title;
    private String content;
    private List<Label> labels;
    private Status status = Status.ACTIVE;
}
