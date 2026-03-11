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

// my commit:
/**

import Entity.Car;

import java.util.*;

public class CarArrayList {
    private Car[] elements;
    private int size;


    public CarArrayList() {
        elements = new Car[10];
    }


    public void add(Car car){
        if (size == elements.length) {
            Car[] newArray = new Car[elements.length * 2];
            System.arraycopy(elements, 0, newArray, 0, elements.length);
            elements = newArray;
        }
        elements[size] = car;
        size++;

    }

    public Car get(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("This collection hasn't this index!");
        }
        return elements[index];
    }


    public int size(){
        return size;
    }

    public Car[] toArray(){
        Car[] result = new Car[size];
        System.arraycopy(elements, 0, result, 0, size);
        return result;
    }

}*/
