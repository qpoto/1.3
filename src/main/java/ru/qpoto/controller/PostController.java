package ru.qpoto.controller;

import ru.qpoto.repository.PostRepository;
import ru.qpoto.repository.impl.GsonPostRepositoryImpl;

public class PostController {
    private final PostRepository postRepository = new GsonPostRepositoryImpl();
}
