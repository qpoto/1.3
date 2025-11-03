package ru.qpoto.repository.impl;

import ru.qpoto.model.Post;
import ru.qpoto.repository.PostRepository;

import java.util.List;

public class GsonPostRepositoryImpl implements PostRepository {
    @Override
    public Post findById(Long id) {
        return null;
    }

    @Override
    public List<Post> findAll() {
        return List.of();
    }

    @Override
    public void save(Post entity) {

    }

    @Override
    public void update(Post entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
