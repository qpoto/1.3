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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GsonPostRepositoryImpl implements PostRepository {
    private final String path = "posts.json";
    @Override
    public Post findById(Long id) {
        List<Post> posts = findAll();
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Post> findAll() {
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            Type listType = new TypeToken<List<Post>>() {
            }.getType();
            List<Post> posts = gson.fromJson(reader, listType);
            return posts != null ? posts : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(Post entity) {
        List<Post> posts = findAll();
        Optional<Post> lasPost = posts.stream().max(Comparator.comparing(Post::getId));
        Long id = lasPost.isPresent() ? lasPost.get().getId() : Long.valueOf(0L);
        entity.setId(++id);
        posts.add(entity);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (BufferedWriter jsonFileWriter = new BufferedWriter(new FileWriter(path))) {
            gson.toJson(posts, jsonFileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Post entity) {
        List<Post> posts = findAll();
        Post post = posts.stream().filter(w -> w.getId().equals(entity.getId())).findFirst().orElse(null);
        if (post != null && !post.equals(entity)) {
            posts.set(posts.indexOf(post), entity);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (BufferedWriter jsonFileWriter = new BufferedWriter(new FileWriter(path))) {
                gson.toJson(posts, jsonFileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Long id) {
        List<Post> posts = findAll();
        Post post = posts.stream().filter(w -> w.getId().equals(id)).findFirst().orElse(null);
        if (post != null) {
            post.setStatus(Status.DELETED);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (BufferedWriter jsonFileWriter = new BufferedWriter(new FileWriter(path))) {
                gson.toJson(posts, jsonFileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteLabel(Long id) {
        List<Post> posts = findAll();
        posts.forEach(post ->
                post.getLabels().stream()
                        .filter(label -> label.getId().equals(id))
                        .findFirst()
                        .ifPresent(label -> label.setStatus(Status.DELETED))
        );
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (BufferedWriter jsonFileWriter = new BufferedWriter(new FileWriter(path))) {
            gson.toJson(posts, jsonFileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
