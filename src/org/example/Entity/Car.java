package org.example.Entity;

public class Car implements Sortable {
    private int power;
    private String model;
    private int year;

    public Car(int power, String model, int year) {
        this.power = power;
        this.model = model;
        this.year = year;
    }

    public int getPower() {
        return power;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    @Override
    public Object getFieldValue(String fieldName) {
        switch (fieldName) {
            case "power":
                return power;
            case "model":
                return model;
            case "year":
                return year;
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
    }
}