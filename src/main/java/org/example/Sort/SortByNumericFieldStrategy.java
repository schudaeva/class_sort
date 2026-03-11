package org.example.Sort;

import org.example.Entity.Sortable;

public class SortByNumericFieldStrategy implements SortStrategy {
    private final String fieldName;
    private final boolean ascending;  // true - по возрастанию, false - по убыванию
    public SortByNumericFieldStrategy(String fieldName, boolean ascending) {
        this.fieldName = fieldName;
        this.ascending = ascending;
    }

    public SortByNumericFieldStrategy(String fieldName) {
        this(fieldName, true);  // по умолчанию по возрастанию
    }

    @Override
    public void sort(Sortable[] items) {}

    @Override
    public String getName() {
        return "По " + fieldName + (ascending ? " (возрастанию)" : " (убыванию)");
    }
}
