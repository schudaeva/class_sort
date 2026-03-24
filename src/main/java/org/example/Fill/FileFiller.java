package org.example.Fill;

import org.example.Collection.SortableArrayList;
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

    // Конструктор по умолчанию использует CarFactory
    public FileFiller(String filename) {
        this(filename, new CarFactory());
    }

    // Основной конструктор с возможностью указать свою фабрику
    public FileFiller(String filename, SortableFactory factory) {
        this.filename = filename;
        this.factory = factory;
    }

    @Override
    public SortableArrayList<Sortable> fill(int length) {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Файл не найден: " + filename);
        }
        SortableArrayList<Sortable> collection = new SortableArrayList<>();
        try {
            Files.lines(path)           // читаем все строки файла
                    .limit(length)             // берем только нужное количество
                    .map(this::safeParseLine)  // преобразуем каждую строку в объект
                    .filter(Objects::nonNull)  // пропускаем null (битые строки)
                    .forEach(collection::add);
            return collection;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла:" + filename, e);
        }
    }

    // Безопасное преобразование строки в объект, при ошибке возвращает null
    private Sortable safeParseLine(String line) {
        try {
            return factory.createFromString(line);
        } catch (IllegalArgumentException e) {
            System.err.println("Пропущена битая строка: " + line + " (" + e.getMessage() + ")");
            return null;
        }
    }
}
