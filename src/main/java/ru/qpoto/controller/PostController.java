package ru.qpoto.controller;

import ru.qpoto.model.Post;
import ru.qpoto.repository.PostRepository;
import ru.qpoto.repository.WriterRepository;

import java.util.List;

public class PostController {
    private final PostRepository postRepository;
    private final WriterRepository writerRepository;

    public PostController(PostRepository postRepository, WriterRepository writerRepository) {
        this.postRepository = postRepository;
        this.writerRepository = writerRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public void delete(Long id) {
        postRepository.delete(id);
        writerRepository.deletePost(id);
    }

    public void update(Post post) {
        postRepository.update(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id);
    }
}
