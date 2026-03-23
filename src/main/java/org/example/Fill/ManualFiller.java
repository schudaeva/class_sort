package org.example.Fill;

import org.example.Entity.Car;
import org.example.Entity.Sortable;

import java.util.Scanner;

public class ManualFiller implements DataFiller{
    private final Scanner scanner;

    // Конструктор инициализирует сканнер для чтения из консоли
    public ManualFiller() {
        this.scanner = new Scanner(System.in);
    }

    // Заполняет массив объектами, введенными пользователем вручную
    @Override
    public Sortable[] fill(int length) {
        Sortable[] arrayCar = new Sortable[length];
        System.out.println("Введите машины: " + length);

        // Цикл ввода данных для каждой машины
        for (int i = 0; i < length; i++) {
            System.out.println("Автомобиль " + (i+1) + " из " + length);

            Car car = inputCar();
            arrayCar[i] = car;

            System.out.println("\n" + car + "\n");
        }

        return arrayCar;
    }

    // Метод для ввода данных одного автомобиля с валидацией
    private Car inputCar() {
        while (true) {
            try {
                System.out.print("Модель: ");
                String model = scanner.nextLine().trim();

                System.out.print("Мощность: ");
                int power = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Год выпуска: ");
                int year = Integer.parseInt(scanner.nextLine().trim());

                // Создаем автомобиль через билдер
                return new Car.Builder()
                        .model(model)
                        .power(power)
                        .year(year)
                        .build();

            } catch (NumberFormatException e) {
                // Ошибка если введены не числа
                System.out.println("Ошибка: мощность и год должны быть числами! Введите данные заново");
            } catch (IllegalArgumentException e) {
                // Ошибка если данные не прошли валидацию (например, отрицательная мощность)
                System.out.println("Ошибка: " + e.getMessage() + "!" + " Введите данные заново");
            }
        }
    }

    @Override
    public String getName() {
        return "Заполнение вручную";
    }
}
