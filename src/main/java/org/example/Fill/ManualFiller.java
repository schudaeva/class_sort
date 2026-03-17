package org.example.Fill;

import org.example.Entity.Car;
import org.example.Entity.Sortable;

import java.util.Scanner;

public class ManualFiller implements DataFiller{
    private final Scanner scanner;

    public ManualFiller() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Sortable[] fill(int length) {
        Sortable[] arrayCar = new Sortable[length];
        System.out.println("Введите количество машин: " + length);

        for (int i = 0; i < length; i++) {
            System.out.println("Автомобиль " + (i+1) + " из " + length);

            Car car = inputCar();
            arrayCar[i] = car;

            System.out.println("\n" + car + "\n");
        }

        return arrayCar;
    }

    private Car inputCar() {
        while (true) {
            try {
                System.out.print("Модель: ");
                String model = scanner.nextLine().trim();

                System.out.print("Мощность: ");
                int power = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Год выпуска: ");
                int year = Integer.parseInt(scanner.nextLine().trim());

                 return new Car.Builder()
                        .model(model)
                        .power(power)
                        .year(year)
                        .build();

            } catch (NumberFormatException e) {
                System.out.println("Ошибка: мощность и год должны быть числами! Введите данные заново");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + "!" + " Введите данные заново");
            }
        }
    }
}
