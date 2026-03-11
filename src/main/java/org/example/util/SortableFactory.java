package org.example.util;

import org.example.Entity.Sortable;

public interface SortableFactory {
    /*
     * Создает объект из строки
     * return созданный объект
     * throws IllegalArgumentException если строка имеет неверный формат
     */
    Sortable createFromString(String line);
}
