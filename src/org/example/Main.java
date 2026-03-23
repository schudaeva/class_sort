package org.example;

import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.example.Sort.SortStrategy;
import org.example.Sort.SortByNumericFieldStrategy;
import org.example.Sort.SortByStringFieldStrategy;
import org.example.Sort.EvenOddSortByPowerStrategy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Sortable[] cars = new Sortable[] {
                new Car(150, "BMW", 2018),
                new Car(120, "Audi", 2020),
                new Car(200, "Mercedes", 2015),
                new Car(180, "Toyota", 2022)
        };

        while (true) {
            System.out.println("\n=== Выберите стратегию сортировки ===");
            System.out.println("1 - Sort by Power");
            System.out.println("2 - Sort by Model");
            System.out.println("3 - Sort by Year");
            System.out.println("4 - Even/Odd Sort by Power");
            System.out.println("0 - Выход");

            int choice = scanner.nextInt();

            if (choice == 0) break;

            SortStrategy strategy = null;

            switch (choice) {
                case 1 -> strategy = new SortByNumericFieldStrategy("power", true);
                case 2 -> strategy = new SortByStringFieldStrategy("model", true);
                case 3 -> strategy = new SortByNumericFieldStrategy("year", true);
                case 4 -> strategy = new EvenOddSortByPowerStrategy();
                default -> {
                    System.out.println("Неверный выбор");
                    continue;
                }
            }

            strategy.sort(cars);

            System.out.println("Результат:");
            for (Sortable item : cars) {
                Car car = (Car) item;
                System.out.println("Power: " + car.getPower() + ", Model: " + car.getModel() + ", Year: " + car.getYear());
            }
        }
    }
}