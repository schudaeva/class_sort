package org.example.Collection;

import org.example.Entity.Sortable;

import java.util.Arrays;

public class SortableArrayList<T extends Sortable> extends SortableCollection<T> {
    private Object[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public SortableArrayList() {
        this.elements = (T[]) new Sortable[10];
        this.size = 0;
    }
    public SortableArrayList(Sortable[] items) {
        this.elements = Arrays.copyOf(items, items.length, items.getClass());
        this.size = items.length;
    }


    @SuppressWarnings("unchecked")
    public T get(int index) {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }else{
            return (T) elements[index]/*.copy()*/; // тут будет инкапсуляция, возвращать будем новый кар
        }
    }

    public T getOrNull(int index) {
        try{
            return get(index);
        }catch (IndexOutOfBoundsException e){
            return null;
        }

    }



    @Override
    public boolean add(T item) {
        expandArray();
        elements[size++] = item;
        return true;
    }

    public int size() {
        return size;
    }



    @SuppressWarnings("unchecked")
    @Override
    public T[] toArray() {
        return (T[]) Arrays.copyOf(elements, size);
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private void expandArray() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    }
}

