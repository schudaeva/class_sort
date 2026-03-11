package org.example.Sort;

import org.example.Entity.Sortable;

public interface SortStrategy {
    void sort(Sortable[] items);

    default String getName() {
        return this.getClass().getSimpleName();
    }


}
