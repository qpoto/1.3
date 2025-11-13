package ru.qpoto.repository;

import ru.qpoto.model.Writer;

public interface WriterRepository extends GenericRepository<Writer, Long> {
    void deletePost(Long id);

    void deleteLabel(Long id);
}
