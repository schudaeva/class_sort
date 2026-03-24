package org.example.Entity;

public class Car implements Sortable{
    private final int power;      // Мощность
    private final String model;    // Модель
    private final int year;        // Год выпуска

    private Car(Builder builder) {
        this.power = builder.power;
        this.model = builder.model;
        this.year = builder.year;
    }
    @Override
    public int getNumericField(String fieldName) {
        return switch (fieldName) {
            case "power" -> power;
            case "year" -> year;
            default -> throw new IllegalArgumentException(
                    "Неизвестное числовое поле: " + fieldName +
                            ". Доступные поля: power, year");
        };
    }

    @Override
    public String getStringField(String fieldName) {
        if ("model".equals(fieldName)) {
            return model;
        }
        throw new IllegalArgumentException(
                "Неизвестное строковое поле: " + fieldName +
                        ". Доступное поле: model");
    }

    @Override
    public String toFileString() {
        return power + "," + model + "," + year;
    }

    @Override
    public boolean isValid() {
        return power > 0 && model != null && !model.trim().isEmpty()
                && year >= 1886 && year <= 2026;
    }

    @Override
    public String[] getAvailableNumericFields() {
        return new String[]{"power", "year"};
    }

    @Override
    public String[] getAvailableStringFields() {
        return new String[]{"model"};
    }

    @Override
    public Sortable copy() {
        return new Car.Builder()
                .power(this.power)
                .model(this.model)
                .year(this.year)
                .build();
    }

    // Статический метод для создания из строки
    public static Car fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid line: " + line);
        }
        return new Car.Builder()
                .power(Integer.parseInt(parts[0]))
                .model(parts[1])
                .year(Integer.parseInt(parts[2]))
                .build();
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


    @Override
    public Object getFieldValue(String fieldName) {
        return switch (fieldName) {
            case "power" -> power;
            case "model" -> model;
            case "year" -> year;
            default -> throw new IllegalArgumentException("Unknown field: " + fieldName);
        };
    }
}