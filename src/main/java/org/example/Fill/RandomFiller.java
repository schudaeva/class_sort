package org.example.Fill;


import org.example.Collection.SortableArrayList;
import org.example.Entity.Car;
import org.example.Entity.Sortable;

import java.util.Random;
import java.util.stream.Stream;

public class RandomFiller implements DataFiller {

    // Диапазон значений для мощности автомобиля
    private static final int MIN_POWER = 50;
    private static final int MAX_POWER = 500;

    // Диапазон значений для года выпуска
    private static final int MIN_YEAR = 1990;
    private static final int MAX_YEAR = 2025;
    private static final String[] MODELS = {
            "Toyota", "BMW", "Lada", "Honda", "Ford",
            "Audi", "Mercedes", "Kia", "Hyundai", "Volkswagen",
            "Nissan", "Mazda", "Subaru", "Chevrolet", "Skoda",
            "Peugeot", "Renault", "Volvo", "Porsche", "Tesla",
            "Lexus", "Jaguar", "Land Rover", "Mitsubishi", "Suzuki"
    };

    private final Random rand = new Random();

    // Заполняет коллекцию случайно сгенерированными автомобилями
    @Override
    public SortableArrayList<Sortable> fill(int length) {
        SortableArrayList<Sortable> collection = new SortableArrayList<>();
        Stream.generate(this::generateRandomCar)
                .limit(length)
                .forEach(collection::add);

        return collection;
    }

    // Генерирует один случайный автомобиль
    private Car generateRandomCar() {
        // Случайная модель из списка
        String model = MODELS[rand.nextInt(MODELS.length)];

        // Случайная мощность в заданном диапазоне
        int power = MIN_POWER + rand.nextInt(MAX_POWER - MIN_POWER + 1);

        // Случайный год выпуска в заданном диапазоне
        int year = MIN_YEAR + rand.nextInt(MAX_YEAR - MIN_YEAR + 1);

        return new Car.Builder()
                .model(model)
                .power(power)
                .year(year)
                .build();
    }

    @Override
    public String getName() {
        return "Заполнение рандомными значениями";
    }
}
