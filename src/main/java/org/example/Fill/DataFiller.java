package org.example.Fill;

import org.example.Collection.SortableArrayList;
import org.example.Entity.Sortable;

public interface DataFiller {

    // Заполняет коллекцию объектами Sortable указанной длины
    SortableArrayList<Sortable> fill(int length);

    // Возвращает простое имя класса (например, "RandomFiller")
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
