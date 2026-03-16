package org.example.Fill;

import org.example.Entity.Sortable;
import org.example.util.CarFactory;
import org.example.util.SortableFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileFiller implements DataFiller {
    private final String filename;
    private final SortableFactory factory;

    public FileFiller(String filename) {
        this(filename, new CarFactory());  // По умолчанию для Car
    }

    public FileFiller(String filename, SortableFactory factory) {
        this.filename = filename;
        this.factory = factory;
    }

    @Override
    public Sortable[] fill(int length) {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Файл не найден: " + filename);
        }
        try {
            return Files.lines(path)
                    .limit(length)
                    .map(this::safeParseLine)
                    .filter(Objects::nonNull)
                    .toArray(Sortable[]::new);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла:" + filename,e);
        }
    }

    private Sortable safeParseLine(String line) {
        try {
            return factory.createFromString(line);
        } catch (IllegalArgumentException e) {
            System.err.println("Пропущена битая строка: " + line + " (" + e.getMessage() + ")");
            return null;
        }
    }
}
