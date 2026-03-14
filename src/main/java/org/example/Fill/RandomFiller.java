package org.example.Fill;


import org.example.Entity.Car;
import org.example.Entity.Sortable;

import java.util.Random;
import java.util.stream.Stream;

public class RandomFiller implements DataFiller{

    private static final int MIN_POWER = 50;
    private static final int MAX_POWER = 500;
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

    public Sortable[] fill(int length) {
       return Stream.generate(this::generateRandomCar)
               .limit(length)
               .toArray(Sortable[]::new);
    }

    private Car generateRandomCar() {
        String model = MODELS[rand.nextInt(MODELS.length)];
        int power = MIN_POWER + rand.nextInt(MAX_POWER - MIN_POWER + 1);
        int year = MIN_YEAR + rand.nextInt(MAX_YEAR - MIN_YEAR + 1);

        return new Car.Builder()
                .model(model)
                .power(power)
                .year(year)
                .build();
    }
}
