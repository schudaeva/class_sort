package org.example.Fill;

import org.example.Entity.Sortable;

public interface DataFiller {
    Sortable[] fill(int length);

    default String getName() {
        return this.getClass().getSimpleName();
    }
}
