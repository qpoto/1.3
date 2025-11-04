package ru.qpoto.model;

import java.util.List;

public class Writer {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private Status status = Status.ACTIVE;
}
