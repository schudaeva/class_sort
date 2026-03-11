package org.example.Collection;

import org.example.Entity.Sortable;

public class FileSaver {
    public static void appendToFile(String filename, Sortable[] items) {
        //Сохраняет массив Sortable объектов в файл (режим добавления)
    }
    public static void appendToFile(String filename, SortableCollection collection) {
        // Сохраняет коллекцию Sortable объектов
        appendToFile(filename, collection.toArray());
    }

}

// мой коммит:
/*
package Collection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileSaver {

    public <T> boolean appendValues(Path path, List<T> values) {
        return write(path, values,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    public <T> boolean writeValues(Path path, List<T> values) {
        return write(path, values,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    private <T> boolean write(Path path, List<T> values, StandardOpenOption... options) {
        try {
            List<String> lines = values.stream()
                    .map(String::valueOf)
                    .toList();

            Files.write(path, lines, options);
            return true;
        } catch (IOException e) {
            // Здесь типа должно быть логирование, например через logback, но мне пока рано это изучать
            return false;
        }
    }


}
*/
