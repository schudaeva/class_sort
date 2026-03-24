package org.example;

import org.example.Collection.SortableArrayList;
import org.example.Collection.SortableCollection;
import org.example.Entity.Car;
import org.example.File.JsonManager;
import org.example.util.Menu;

public class Application {
    public static void main(String[] args) {

        JsonManager json = new JsonManager(Car.class, SortableArrayList::new, "file.json") {};
        json.clear();

        SortableCollection<Car> cars = new SortableArrayList<>();
        cars.add(new Car.Builder().power(110).model("рено").year(1999).build());
        cars.add(new Car.Builder().power(1312).model("lamborgini").year(2000).build());
        SortableCollection<Car> cars2 = new SortableArrayList<>();
        cars2.add(new Car.Builder().power(120).model("пежо").year(1914).build());
        cars2.add(new Car.Builder().power(103).model("солярис").year(2018).build());


        json.append(cars);
        json.append(cars2);

        Car car = new Car.Builder().power(77).model("Запорожец").year(1977).build();
        json.appendValues(car, 1);

        for (int i = 0; i < json.loadArray().length; i++) {
            System.out.println(json.loadArray()[i]);
        }
        System.out.println(json.load());




        Menu menu = new Menu();
        menu.start();
    }

}