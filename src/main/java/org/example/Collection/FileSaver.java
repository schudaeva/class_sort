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
