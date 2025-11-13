package ru.qpoto.repository;

import ru.qpoto.model.Post;

public interface PostRepository extends GenericRepository <Post, Long> {
    void deleteLabel(Long id);
}
