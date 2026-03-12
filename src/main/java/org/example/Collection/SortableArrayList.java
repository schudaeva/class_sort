package org.example.Collection;

import org.example.Entity.Sortable;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;

public class SortableArrayList<Entity> extends AbstractList<Entity> implements SortableCollection<Entity> {
    private Entity[] elements;
    private int size;

    public SortableArrayList() {}

    @Override
    public void add(Entity item) {

    }

    @Override
    public Entity get(int index) {
        return elements[index]/*.copy()*/; // тут будет инкапсуляция, возвращать будем новый кар
    }

    public SortableArrayList(Sortable[] items) {
        this.elements = Arrays.copyOf(items, items.length);
        this.size = items.length;
    }


    public void add(Sortable item) {

    }

//
//    public Sortable get(int index) {
//        return null;
//    }


    public int size() {
        return 0;
    }


    public Sortable[] toArray() {
        return new Sortable[0];
    }

    @Override
    public Iterator<Sortable> iterator() {
        return null;
    }

//    @Override
//    public Iterator<Sortable> iterator() {
//        return null;
//    }

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
