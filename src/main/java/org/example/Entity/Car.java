package org.example.Entity;

public class Car {
    private final int power;      // Мощность
    private final String model;    // Модель
    private final int year;        // Год выпуска

    private Car(Builder builder) {
        this.power = builder.power;
        this.model = builder.model;
        this.year = builder.year;
    }

    public int getPower() { return power; }
    public String getModel() { return model; }
    public int getYear() { return year; }


    public static class Builder {
        private int power;
        private String model;
        private int year;

        public Builder power(int power) {
            this.power = power;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            if (model == null || model.trim().isEmpty()) {
                throw new IllegalArgumentException("Модель не может быть пустой");
            }
            if (power <= 0) {
                throw new IllegalArgumentException("Мощность должна быть положительной");
            }
            if (year < 1886 || year > 2026) { // Первый авто - 1886
                throw new IllegalArgumentException("Некорректный год");
            }
            return new Car(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Автомобиль: %s | Мощность: %d л.с. | Год: %d",
                model, power, year);
    }
}
