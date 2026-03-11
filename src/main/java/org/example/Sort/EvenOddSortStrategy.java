package org.example.Sort;

import org.example.Entity.Sortable;

public class EvenOddSortStrategy implements SortStrategy {
    private final String fieldName;  // числовое поле

    public EvenOddSortStrategy(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public void sort(Sortable[] items) {}

    @Override
    public String getName() {
        return "Четные по " + fieldName;
    }
}
