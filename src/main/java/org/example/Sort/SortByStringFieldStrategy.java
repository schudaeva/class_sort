package org.example.Sort;

import org.example.Entity.Sortable;

public class SortByStringFieldStrategy implements SortStrategy {
    private final String fieldName;
    private final boolean ascending; // true - по возрастанию, false - по убыванию

    public SortByStringFieldStrategy(String fieldName, boolean ascending) {
        this.fieldName = fieldName;
        this.ascending = ascending;
    }

    public SortByStringFieldStrategy(String fieldName) {
        this(fieldName, true);
    }

    @Override
    public void sort(Sortable[] items) {}

    @Override
    public String getName() {
        return "По " + fieldName + (ascending ? " (А-Я)" : " (Я-А)");
    }
}
