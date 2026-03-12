package org.example.Collection;

import org.example.Entity.*;


import java.util.AbstractList;

public interface SortableCollection<Entity> extends Iterable<Sortable> {                        //extends AbstractList<Car>

    void add(Entity item); // Добавляет элемент в коллекцию
    Entity get(int index); // Возвращает элемент по индексу
    int size();
    Sortable[] toArray();  // Преобразует коллекцию в массив
    default boolean isEmpty() {
        return size() == 0;
    }



}


















