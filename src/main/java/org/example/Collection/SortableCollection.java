package org.example.Collection;

import org.example.Entity.Sortable;

public interface SortableCollection extends Iterable<Sortable>  {
    void add(Sortable item); // Добавляет элемент в коллекцию
    Sortable get(int index); // Возвращает элемент по индексу
    int size();
    Sortable[] toArray();
    default boolean isEmpty() {
        return size() == 0;
    } // Преобразует коллекцию в массив
}
