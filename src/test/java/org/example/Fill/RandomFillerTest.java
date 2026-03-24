package org.example.Fill;

import org.example.Collection.SortableArrayList;
import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class RandomFillerTest {

    private RandomFiller filler;

    @BeforeEach
    void setUp() {
        filler = new RandomFiller();
    }


    @Test
    @DisplayName("Заполнение коллекции с положительной длиной")
    void testFillWithPositiveLength() {
        int length = 10;
        SortableArrayList<Sortable> result = filler.fill(length);

        assertEquals(length, result.size(), "Размер коллекции должен совпадать с запрошенной длиной");
    }

    @Test
    @DisplayName("Заполнение коллекции с длиной 1")
    void testFillWithLengthOne() {
        SortableArrayList<Sortable> result = filler.fill(1);

        assertEquals(1, result.size());
        assertNotNull(result.get(0), "Элемент не должен быть null");
    }

    @Test
    @DisplayName("Заполнение пустой коллекции (длина 0)")
    void testFillWithZeroLength() {
        SortableArrayList<Sortable> result = filler.fill(0);

        assertEquals(0, result.size());
    }


    @Test
    @DisplayName("Мощность автомобиля в заданном диапазоне")
    void testPowerInRange() {
        int length = 100;
        SortableArrayList<Sortable> result = filler.fill(length);

        for (Sortable sortable : result) {
            Car car = (Car) sortable;
            int power = car.getPower();
            assertTrue(power >= 50 && power <= 500,
                    "Мощность должна быть в диапазоне 50-500, но была: " + power);
        }
    }

    @Test
    @DisplayName("Год выпуска автомобиля в заданном диапазоне")
    void testYearInRange() {
        int length = 100;
        SortableArrayList<Sortable> result = filler.fill(length);

        for (Sortable sortable : result) {
            Car car = (Car) sortable;
            int year = car.getYear();
            assertTrue(year >= 1990 && year <= 2025,
                    "Год должен быть в диапазоне 1990-2025, но был: " + year);
        }
    }

    @Test
    @DisplayName("Модель автомобиля из списка допустимых")
    void testModelFromAllowedList() {
        String[] allowedModels = {
                "Toyota", "BMW", "Lada", "Honda", "Ford",
                "Audi", "Mercedes", "Kia", "Hyundai", "Volkswagen",
                "Nissan", "Mazda", "Subaru", "Chevrolet", "Skoda",
                "Peugeot", "Renault", "Volvo", "Porsche", "Tesla",
                "Lexus", "Jaguar", "Land Rover", "Mitsubishi", "Suzuki"
        };

        int length = 100;
        SortableArrayList<Sortable> result = filler.fill(length);

        for (Sortable sortable : result) {
            Car car = (Car) sortable;
            String model = car.getModel();
            boolean isValid = false;
            for (String allowed : allowedModels) {
                if (allowed.equals(model)) {
                    isValid = true;
                    break;
                }
            }
            assertTrue(isValid, "Модель должна быть из списка допустимых, но была: " + model);
        }
    }


    @Test
    @DisplayName("Сгенерированные автомобили валидны")
    void testGeneratedCarsAreValid() {
        int length = 50;
        SortableArrayList<Sortable> result = filler.fill(length);

        for (Sortable sortable : result) {
            Car car = (Car) sortable;
            assertTrue(car.isValid(), "Сгенерированный автомобиль должен быть валидным");
        }
    }


    @Test
    @DisplayName("Проверка имени стратегии")
    void testGetName() {
        assertEquals("Заполнение рандомными значениями", filler.getName());
    }
}