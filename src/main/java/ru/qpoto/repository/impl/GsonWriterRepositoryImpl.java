package ru.qpoto.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.qpoto.model.Status;
import ru.qpoto.model.Writer;
import ru.qpoto.repository.WriterRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonWriterRepositoryImpl implements WriterRepository {

    private final String path = "src/main/resources/writers.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Writer findById(Long id) {
        return findAll().stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Writer> findAll() {
        return readWritersFromFile();
    }

    @Override
    public void save(Writer writer) {
        List<Writer> writers = readWritersFromFile();
        writer.setId(generatedId(writers));
        writers.add(writer);
        writeWritersToFile(writers);
    }

    @Override
    public void update(Writer entity) {
        List<Writer> writers = readWritersFromFile();
        Writer existing = writers.stream()
                .filter(w -> w.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);
        if (existing != null && !existing.equals(entity)) {
            int index = writers.indexOf(existing);
            writers.set(index, entity);
            writeWritersToFile(writers);
        }
    }

    @Override
    public void delete(Long id) {
        List<Writer> writers = readWritersFromFile();
        writers.stream()
                .filter(writer -> writer.getId().equals(id))
                .findFirst()
                .ifPresent(writer -> writer.setStatus(Status.DELETED));
        writeWritersToFile(writers);
    }

    @Override
    public void deletePost(Long id) {
        List<Writer> writers = readWritersFromFile();
        writers.forEach(writer ->
                writer.getPosts().stream()
                        .filter(post -> post.getId().equals(id))
                        .findFirst()
                        .ifPresent(post -> post.setStatus(Status.DELETED))
        );
        writeWritersToFile(writers);
    }

    @Override
    public void deleteLabel(Long id) {
        List<Writer> writers = readWritersFromFile();
        writers.forEach(writer ->
                writer.getPosts().forEach(post ->
                        post.getLabels().stream()
                                .filter(label -> label.getId().equals(id))
                                .findFirst()
                                .ifPresent(label -> label.setStatus(Status.DELETED))
                )
        );
        writeWritersToFile(writers);
    }

    private Long generatedId(List<Writer> writers) {
        return writers.stream()
                .mapToLong(Writer::getId)
                .max()
                .orElse(0L) + 1;
    }

    private void writeWritersToFile(List<Writer> writers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            gson.toJson(writers, writer);
        } catch (IOException e) {
            System.out.println("Не удалось обновить writers");;
        }
    }

    private List<Writer> readWritersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            Type listType = new TypeToken<List<Writer>>() {}.getType();
            List<Writer> writers = gson.fromJson(reader, listType);
            return writers != null ? writers : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}