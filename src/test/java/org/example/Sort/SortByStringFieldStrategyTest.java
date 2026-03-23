package org.example.Sort;

import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class SortByStringFieldStrategyTest {


    @Test
    @DisplayName("Сортировка по модели по возрастанию (А-Я)")
    void testSortByModelAscending() {
        SortByStringFieldStrategy strategy = new SortByStringFieldStrategy("model", true);

        Sortable[] cars = new Sortable[]{
                createCar(100, "Toyota", 2020),
                createCar(150, "Audi", 2021),
                createCar(200, "BMW", 2022)
        };

        strategy.sort(cars);

        assertEquals("Audi", cars[0].getStringField("model"));
        assertEquals("BMW", cars[1].getStringField("model"));
        assertEquals("Toyota", cars[2].getStringField("model"));
    }

    @Test
    @DisplayName("Сортировка по модели по убыванию (Я-А)")
    void testSortByModelDescending() {
        SortByStringFieldStrategy strategy = new SortByStringFieldStrategy("model", false);

        Sortable[] cars = new Sortable[]{
                createCar(100, "Audi", 2020),
                createCar(150, "Toyota", 2021),
                createCar(200, "BMW", 2022)
        };

        strategy.sort(cars);

        assertEquals("Toyota", cars[0].getStringField("model"));
        assertEquals("BMW", cars[1].getStringField("model"));
        assertEquals("Audi", cars[2].getStringField("model"));
    }


    @Test
    @DisplayName("Сортировка по модели с учетом регистра")
    void testSortByModelCaseSensitive() {
        SortByStringFieldStrategy strategy = new SortByStringFieldStrategy("model", true);

        Sortable[] cars = new Sortable[]{
                createCar(100, "audi", 2020),
                createCar(150, "BMW", 2021),
                createCar(200, "Audi", 2022)
        };

        strategy.sort(cars);

        // Сравнение строк с учетом регистра: "Audi" < "BMW" < "audi"
        assertEquals("Audi", cars[0].getStringField("model"));
        assertEquals("BMW", cars[1].getStringField("model"));
        assertEquals("audi", cars[2].getStringField("model"));
    }


    @Test
    @DisplayName("Пустой массив")
    void testEmptyArray() {
        SortByStringFieldStrategy strategy = new SortByStringFieldStrategy("model", true);
        Sortable[] empty = new Sortable[0];

        assertDoesNotThrow(() -> strategy.sort(empty));
    }

    @Test
    @DisplayName("Массив из одного элемента")
    void testSingleElement() {
        SortByStringFieldStrategy strategy = new SortByStringFieldStrategy("model", true);
        Sortable[] cars = new Sortable[]{createCar(100, "Toyota", 2020)};

        strategy.sort(cars);

        assertEquals("Toyota", cars[0].getStringField("model"));
    }

    @Test
    @DisplayName("Массив с одинаковыми значениями")
    void testEqualValues() {
        SortByStringFieldStrategy strategy = new SortByStringFieldStrategy("model", true);

        Sortable[] cars = new Sortable[]{
                createCar(100, "Toyota", 2020),
                createCar(150, "Toyota", 2021),
                createCar(200, "Toyota", 2022)
        };

        Sortable[] original = cars.clone();
        strategy.sort(cars);

        for (Sortable car : cars) {
            assertEquals("Toyota", car.getStringField("model"));
        }
    }


    @Test
    @DisplayName("Массив с пробелами в начале/конце")
    void testStringsWithSpaces() {
        SortByStringFieldStrategy strategy = new SortByStringFieldStrategy("model", true);

        Sortable[] cars = new Sortable[]{
                createCar(100, "  BMW", 2020),
                createCar(150, "Audi", 2021),
                createCar(200, "Toyota  ", 2022)
        };

        strategy.sort(cars);

        // Сравнение происходит с учетом пробелов
        assertEquals("  BMW", cars[0].getStringField("model"));
        assertEquals("Audi", cars[1].getStringField("model"));
        assertEquals("Toyota  ", cars[2].getStringField("model"));
    }

    @ParameterizedTest
    @CsvSource({
            "Audi, BMW, Toyota, Audi,BMW,Toyota",
            "Toyota, Audi, BMW, Audi,BMW,Toyota",
            "BMW, Toyota, Audi, Audi,BMW,Toyota"
    })
    @DisplayName("Сортировка по модели по возрастанию (разные порядки)")
    void testSortByModelAscendingParameterized(String a, String b, String c,
                                               String e1, String e2, String e3) {
        SortByStringFieldStrategy strategy = new SortByStringFieldStrategy("model", true);

        Sortable[] cars = new Sortable[]{
                createCar(100, a, 2020),
                createCar(150, b, 2021),
                createCar(200, c, 2022)
        };

        strategy.sort(cars);

        assertEquals(e1, cars[0].getStringField("model"));
        assertEquals(e2, cars[1].getStringField("model"));
        assertEquals(e3, cars[2].getStringField("model"));
    }

    @ParameterizedTest
    @CsvSource({
            "Audi, BMW, Toyota, Toyota,BMW,Audi",
            "Toyota, Audi, BMW, Toyota,BMW,Audi",
            "BMW, Toyota, Audi, Toyota,BMW,Audi"
    })
    @DisplayName("Сортировка по модели по убыванию (разные порядки)")
    void testSortByModelDescendingParameterized(String a, String b, String c,
                                                String e1, String e2, String e3) {
        SortByStringFieldStrategy strategy = new SortByStringFieldStrategy("model", false);

        Sortable[] cars = new Sortable[]{
                createCar(100, a, 2020),
                createCar(150, b, 2021),
                createCar(200, c, 2022)
        };

        strategy.sort(cars);

        assertEquals(e1, cars[0].getStringField("model"));
        assertEquals(e2, cars[1].getStringField("model"));
        assertEquals(e3, cars[2].getStringField("model"));
    }


    @Test
    @DisplayName("Проверка имени стратегии (по возрастанию)")
    void testGetNameAscending() {
        SortByStringFieldStrategy strategy = new SortByStringFieldStrategy("model", true);
        assertEquals("Сортировка по model ASC", strategy.getName());
    }

    @Test
    @DisplayName("Проверка имени стратегии (по убыванию)")
    void testGetNameDescending() {
        SortByStringFieldStrategy strategy = new SortByStringFieldStrategy("model", false);
        assertEquals("Сортировка по model DESC", strategy.getName());
    }

    private Car createCar(int power, String model, int year) {
        return new Car.Builder()
                .power(power)
                .model(model)
                .year(year)
                .build();
    }
}