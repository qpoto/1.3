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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GsonLabelRepositoryImpl implements LabelRepository {
    private final String path = "label.json";

    @Override
    public Label findById(Long id) {
        List<Label> labels = findAll();
        return labels.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Label> findAll() {
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            Type listType = new TypeToken<List<Label>>() {
            }.getType();
            List<Label> labels = gson.fromJson(reader, listType);
            return labels != null ? labels : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(Label entity) {
        List<Label> labels = findAll();
        Optional<Label> lasLabel = labels.stream().max(Comparator.comparing(Label::getId));
        Long id = lasLabel.isPresent() ? lasLabel.get().getId() : Long.valueOf(0L);
        entity.setId(++id);
        labels.add(entity);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (BufferedWriter jsonFileWriter = new BufferedWriter(new FileWriter(path))) {
            gson.toJson(labels, jsonFileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Label entity) {
        List<Label> labels = findAll();
        Label label = labels.stream().filter(w -> w.getId().equals(entity.getId())).findFirst().orElse(null);
        if (label != null && !label.equals(entity)) {
            labels.set(labels.indexOf(label), entity);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (BufferedWriter jsonFileWriter = new BufferedWriter(new FileWriter(path))) {
                gson.toJson(labels, jsonFileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Long id) {
        List<Label> labels = findAll();
        Label label = labels.stream().filter(w -> w.getId().equals(id)).findFirst().orElse(null);
        if (label != null) {
            label.setStatus(Status.DELETED);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter jsonFileWriter = new FileWriter(path)) {
                gson.toJson(labels, jsonFileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
