package ru.qpoto.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.qpoto.model.Post;
import ru.qpoto.model.Status;
import ru.qpoto.repository.PostRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonPostRepositoryImpl implements PostRepository {

    private final String path = "src/main/resources/posts.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Post findById(Long id) {
        return findAll().stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Post> findAll() {
        return readPostsFromFile();
    }

    @Override
    public void save(Post entity) {
        List<Post> posts = readPostsFromFile();
        entity.setId(generatedId(posts));
        posts.add(entity);
        writePostsToFile(posts);
    }

    @Override
    public void update(Post entity) {
        List<Post> posts = readPostsFromFile();
        Post existing = posts.stream()
                .filter(post -> post.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);
        if (existing != null && !existing.equals(entity)) {
            int index = posts.indexOf(existing);
            posts.set(index, entity);
            writePostsToFile(posts);
        }
    }

    @Override
    public void delete(Long id) {
        List<Post> posts = readPostsFromFile();
        posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .ifPresent(post -> post.setStatus(Status.DELETED));
        writePostsToFile(posts);
    }

    @Override
    public void deleteLabel(Long id) {
        List<Post> posts = readPostsFromFile();
        posts.forEach(post ->
                post.getLabels().stream()
                        .filter(label -> label.getId().equals(id))
                        .findFirst()
                        .ifPresent(label -> label.setStatus(Status.DELETED))
        );
        writePostsToFile(posts);
    }

    private Long generatedId(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .max()
                .orElse(0L) + 1;
    }

    private void writePostsToFile(List<Post> posts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            gson.toJson(posts, writer);
        } catch (IOException e) {
            System.out.println("Не удалось обновить posts");;
        }
    }

    private List<Post> readPostsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            Type listType = new TypeToken<List<Post>>() {}.getType();
            List<Post> posts = gson.fromJson(reader, listType);
            return posts != null ? posts : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}