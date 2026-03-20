package entity;

public class Car implements Sortable {
    private int power;
    private String model;
    private int year;

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

        public Builder setPower(int power) { this.power = power; return this; }
        public Builder setModel(String model) { this.model = model; return this; }
        public Builder setYear(int year) { this.year = year; return this; }
        public Car build() { return new Car(this); }
    }

    @Override
    public String toString() {
        return model + " | Power: " + power + " | Year: " + year;
    }
}