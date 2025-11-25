package ru.qpoto.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.qpoto.model.Label;
import ru.qpoto.model.Status;
import ru.qpoto.repository.LabelRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonLabelRepositoryImpl implements LabelRepository {

    private final String path = "src/main/resources/labels.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Label findById(Long id) {
        return findAll().stream()
                .filter(label -> label.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Label> findAll() {
        return readLabelsFromFile();
    }

    @Override
    public void save(Label entity) {
        List<Label> labels = findAll();
        entity.setId(generatedId(labels));
        labels.add(entity);
        writeLabelsToFile(labels);
    }

    @Override
    public void update(Label entity) {
        List<Label> labels = findAll();
        Label existing = labels.stream()
                .filter(label -> label.getId().equals(entity.getId()))
                .findFirst()
                .orElse(null);
        if (existing != null && !existing.equals(entity)) {
            labels.set(labels.indexOf(existing), entity);
            writeLabelsToFile(labels);
        }
    }

    @Override
    public void delete(Long id) {
        List<Label> labels = findAll();
        labels.stream()
                .filter(label -> label.getId().equals(id))
                .findFirst()
                .ifPresent(label -> label.setStatus(Status.DELETED));
        writeLabelsToFile(labels);
    }

    private Long generatedId(List<Label> labels) {
        return labels.stream()
                .mapToLong(Label::getId)
                .max()
                .orElse(0L) + 1;
    }

    private void writeLabelsToFile(List<Label> labels) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            gson.toJson(labels, writer);
        } catch (IOException e) {
            System.out.println("Не удалось обновить labels");;
        }
    }

    private List<Label> readLabelsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            Type listType = new TypeToken<List<Label>>() {}.getType();
            List<Label> labels = gson.fromJson(reader, listType);
            return labels != null ? labels : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}