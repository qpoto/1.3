package ru.qpoto.controller;

import ru.qpoto.repository.PostRepository;
import ru.qpoto.view.PostView;

public class PostController {
    private final PostRepository postRepository;
    private final PostView postView;


    public PostController(PostRepository postRepository, PostView postView) {
        this.postRepository = postRepository;
        this.postView = postView;
    }
}
