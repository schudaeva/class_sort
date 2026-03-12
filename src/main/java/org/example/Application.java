package org.example;

import org.example.Collection.SortableCollection;
import org.example.util.Menu;

import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }
    // пример использования
    List<Car> cars = new SortableCollection<>();
    cars.add(/*New Car().Builder.датьМощность(110).датьМодель("рено").build();*/);
    // и так далее
}