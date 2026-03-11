package org.example.Fill;

import org.example.Entity.Sortable;
import org.example.util.CarFactory;
import org.example.util.SortableFactory;

public class FileFiller implements DataFiller {
    private final String filename;
    private final SortableFactory factory;

    public FileFiller(String filename) {
        this(filename, new CarFactory());  // По умолчанию для Car
    }

    public FileFiller(String filename, SortableFactory factory) {
        this.filename = filename;
        this.factory = factory;
    }

    @Override
    public Sortable[] fill(int length) {
        return new Sortable[0];
    }
}
