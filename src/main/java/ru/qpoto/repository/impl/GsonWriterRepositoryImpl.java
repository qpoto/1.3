package ru.qpoto.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.qpoto.model.Post;
import ru.qpoto.model.Status;
import ru.qpoto.model.Writer;
import ru.qpoto.repository.WriterRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GsonWriterRepositoryImpl implements WriterRepository {
    private final String path = "writers.json";

    @Override
    public Writer findById(Long id) {
        List<Writer> writers = findAll();
        return writers.stream().filter(w -> w.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Writer> findAll() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(path)) {
            Type listType = new TypeToken<List<Writer>>() {
            }.getType();
            List<Writer> writers = gson.fromJson(reader, listType);
            return writers != null ? writers : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(Writer writer) {
        List<Writer> writers = findAll();
        Optional<Writer> lastWriter = writers.stream().max(Comparator.comparing(Writer::getId));
        Long id = lastWriter.isPresent() ? lastWriter.get().getId() : Long.valueOf(0L);
        writer.setId(++id);
        writers.add(writer);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter jsonFileWriter = new FileWriter(path)) {
            gson.toJson(writers, jsonFileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Writer entity) {
        List<Writer> writers = findAll();
        Writer writer = writers.stream().filter(w -> w.getId().equals(entity.getId())).findFirst().orElse(null);
        if (writer != null && !writer.equals(entity)) {
            writers.set(writers.indexOf(writer), entity);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter jsonFileWriter = new FileWriter(path)) {
                gson.toJson(writers, jsonFileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void delete(Long id) {
        List<Writer> writers = findAll();
        Writer writer = writers.stream().filter(w -> w.getId().equals(id)).findFirst().orElse(null);
        if (writer != null) {
            writer.setStatus(Status.DELETED);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter jsonFileWriter = new FileWriter(path)) {
                gson.toJson(writers, jsonFileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deletePost(Long id) {
        List<Writer> writers = findAll();
        writers.forEach(writer ->
                        writer.getPosts().stream()
                                .filter(post -> post.getId().equals(id))
                                .findFirst()
                                .ifPresent(post -> post.setStatus(Status.DELETED))
                );
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter jsonFileWriter = new FileWriter(path)) {
            gson.toJson(writers, jsonFileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
