package org.example.Fill;

import org.example.Entity.Sortable;

public interface DataFiller {

    // Заполняет массив объектами Sortable указанной длины
    Sortable[] fill(int length);

    // Возвращает простое имя класса (например, "RandomFiller")
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
