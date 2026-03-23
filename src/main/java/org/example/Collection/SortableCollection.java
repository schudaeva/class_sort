package org.example.Collection;

import org.example.Entity.*;


import java.util.AbstractList;

public abstract class SortableCollection<T extends Sortable> extends AbstractList<T>{// Itterable уже есть в абстракт лист

    public abstract T[] toArray();  // Преобразует коллекцию в массив
    public abstract boolean isEmpty();




}


















