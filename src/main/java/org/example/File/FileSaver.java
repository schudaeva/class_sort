package org.example.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.example.Collection.SortableCollection;
import org.example.Entity.Sortable;

public class FileSaver {
    /*public static void append(String filename, Sortable[] items) {
        //Сохраняет массив Sortable объектов в файл (режим добавления)

    }
    public static void append(String filename, SortableCollection<Sortable> collection) {
        // Сохраняет коллекцию Sortable объектов
        append(filename, collection.toArray());

    }*/

    public <T extends Sortable> boolean append(String filename, SortableCollection<T> collection) {
        // Сохраняет коллекцию Sortable объектов
        Path path = Path.of(filename);
        return appendValues(path, collection);
    }


    public <T extends Sortable> boolean appendValues(Path path, SortableCollection<T> values) {
        return write(path, values,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    public <T extends Sortable> boolean writeValues(Path path, SortableCollection<T> values) {
        return write(path, values,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    private <T extends Sortable> boolean write(Path path, SortableCollection<T> values, StandardOpenOption... options) {
        try {
            List<String> lines = values.stream()
                    .map(Sortable::toFileString)
                    .toList();

            Files.write(path, lines, options);
            return true;
        } catch (IOException e) {
            // Здесь типа должно быть логирование, например через logback, но мне пока рано это изучать, пока что:
            System.err.println("Ошибка записи в файл " + path + ": " + e.getMessage());
            return false;
        }
    }









}