package ru.qpoto.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.qpoto.model.Writer;
import ru.qpoto.repository.WriterRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GsonWriterRepositoryImpl implements WriterRepository {
    private final String path = "writers.json";
    @Override
    public Writer findById(Long id) {
        return null;
    }

    @Override
    public List<Writer> findAll() {
        if (!Files.exists(Paths.get(path))) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(path)) {
            Type listType = new TypeToken<List<Writer>>(){}.getType();
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

    }

    @Override
    public void delete(Long id) {

    }
}
