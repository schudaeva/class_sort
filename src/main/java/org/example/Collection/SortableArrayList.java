package org.example.Collection;

import org.example.Entity.Sortable;

import java.util.Arrays;
import java.util.Iterator;

public class SortableArrayList implements SortableCollection {
    private Sortable[] elements;
    private int size;

    public SortableArrayList() {}
    public SortableArrayList(Sortable[] items) {
        this.elements = Arrays.copyOf(items, items.length);
        this.size = items.length;
    }


    @Override
    public void add(Sortable item) {

    }

    @Override
    public Sortable get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Sortable[] toArray() {
        return new Sortable[0];
    }

    @Override
    public Iterator<Sortable> iterator() {
        return null;
    }
}
