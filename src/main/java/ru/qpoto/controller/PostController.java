package ru.qpoto.controller;

import ru.qpoto.model.Post;
import ru.qpoto.repository.PostRepository;
import ru.qpoto.repository.WriterRepository;
import ru.qpoto.repository.impl.GsonPostRepositoryImpl;
import ru.qpoto.repository.impl.GsonWriterRepositoryImpl;

import java.util.List;

public class PostController {
    private final PostRepository postRepository = new GsonPostRepositoryImpl();
    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();

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
