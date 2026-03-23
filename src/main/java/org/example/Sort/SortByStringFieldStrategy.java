package org.example.Sort;
import org.example.Entity.Sortable;

public class SortByStringFieldStrategy implements SortStrategy {

    private String fieldName;
    private boolean ascending;

    public SortByStringFieldStrategy(String fieldName, boolean ascending) {
        this.fieldName = fieldName;
        this.ascending = ascending;
    }

    @Override
    public void sort(Sortable[] items) {
        for (int i = 0; i < items.length - 1; i++) {
            for (int j = 0; j < items.length - i - 1; j++) {

                String a = (String) items[j].getFieldValue(fieldName);
                String b = (String) items[j + 1].getFieldValue(fieldName);

                int compare = a.compareTo(b);

                if (ascending) {
                    if (compare > 0) {
                        swap(items, j, j + 1);
                    }
                } else {
                    if (compare < 0) {
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