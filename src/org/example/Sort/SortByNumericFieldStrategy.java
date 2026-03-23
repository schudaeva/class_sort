package org.example.Sort;

import org.example.Entity.Sortable;

public class SortByNumericFieldStrategy implements SortStrategy {

    private String fieldName;
    private boolean ascending;

    public SortByNumericFieldStrategy(String fieldName, boolean ascending) {
        this.fieldName = fieldName;
        this.ascending = ascending;
    }

    @Override
    public void sort(Sortable[] items) {
        for (int i = 0; i < items.length - 1; i++) {
            for (int j = 0; j < items.length - i - 1; j++) {

                int a = (int) items[j].getFieldValue(fieldName);
                int b = (int) items[j + 1].getFieldValue(fieldName);

                if (ascending) {
                    if (a > b) {
                        swap(items, j, j + 1);
                    }
                } else {
                    if (a < b) {
                        swap(items, j, j + 1);
                    }
                }
            }
        }
    }

    private void swap(Sortable[] items, int i, int j) {
        Sortable temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    @Override
    public String getName() {
        return "Sort by " + fieldName + (ascending ? " ASC" : " DESC");
    }
}