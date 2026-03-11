package org.example.util;

import org.example.Entity.Car;
import org.example.Entity.Sortable;

public class CarFactory implements SortableFactory {

    @Override
    public Sortable createFromString(String line) {
        return Car.fromFileString(line);
    }
}