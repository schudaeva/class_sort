import entity.Car;
import entity.Sortable;
import sort.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Пример массива автомобилей
        Sortable[] cars = new Sortable[]{
                new Car.Builder().setModel("BMW").setPower(200).setYear(2018).build(),
                new Car.Builder().setModel("Audi").setPower(155).setYear(2016).build(),
                new Car.Builder().setModel("Tesla").setPower(300).setYear(2020).build(),
                new Car.Builder().setModel("Opel").setPower(101).setYear(2015).build()
        };

        while (true) {
            System.out.println("\n=== Выберите стратегию сортировки ===");
            System.out.println("1 - Sort by Power");
            System.out.println("2 - Sort by Model");
            System.out.println("3 - Sort by Year");
            System.out.println("4 - Even/Odd Sort by Power (Доп. задание 1)");
            System.out.println("0 - Выход");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод, введите число!");
                continue;
            }

            SortStrategy strategy = null;

            switch (choice) {
                case 1 -> strategy = new SortByPowerStrategy();
                case 2 -> strategy = new SortByModelStrategy();
                case 3 -> strategy = new SortByYearStrategy();
                case 4 -> strategy = new EvenOddSortByPowerStrategy();
                case 0 -> {
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;
                }
                default -> {
                    System.out.println("Неверный выбор, попробуйте снова.");
                    continue;
                }
            }

            // Выполняем сортировку
            strategy.sort(cars);

            // Выводим результат
            System.out.println("\n=== Результат сортировки (" + strategy.getName() + ") ===");
            for (Sortable s : cars) {
                System.out.println(s);
            }
        }
    }
}