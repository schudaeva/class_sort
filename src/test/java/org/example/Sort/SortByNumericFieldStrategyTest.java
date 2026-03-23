package org.example.Sort;

import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class SortByNumericFieldStrategyTest {


    @Test
    @DisplayName("Сортировка по мощности по возрастанию")
    void testSortByPowerAscending() {
        SortByNumericFieldStrategy strategy = new SortByNumericFieldStrategy("power", true);

        Sortable[] cars = new Sortable[]{
                createCar(300, "A", 2020),
                createCar(100, "B", 2021),
                createCar(200, "C", 2022)
        };

        strategy.sort(cars);

        assertEquals(100, cars[0].getNumericField("power"));
        assertEquals(200, cars[1].getNumericField("power"));
        assertEquals(300, cars[2].getNumericField("power"));
    }

    @Test
    @DisplayName("Сортировка по мощности по убыванию")
    void testSortByPowerDescending() {
        SortByNumericFieldStrategy strategy = new SortByNumericFieldStrategy("power", false);

        Sortable[] cars = new Sortable[]{
                createCar(100, "A", 2020),
                createCar(300, "B", 2021),
                createCar(200, "C", 2022)
        };

        strategy.sort(cars);

        assertEquals(300, cars[0].getNumericField("power"));
        assertEquals(200, cars[1].getNumericField("power"));
        assertEquals(100, cars[2].getNumericField("power"));
    }


    @Test
    @DisplayName("Сортировка по году по возрастанию")
    void testSortByYearAscending() {
        SortByNumericFieldStrategy strategy = new SortByNumericFieldStrategy("year", true);

        Sortable[] cars = new Sortable[]{
                createCar(100, "A", 2022),
                createCar(150, "B", 2020),
                createCar(200, "C", 2021)
        };

        strategy.sort(cars);

        assertEquals(2020, cars[0].getNumericField("year"));
        assertEquals(2021, cars[1].getNumericField("year"));
        assertEquals(2022, cars[2].getNumericField("year"));
    }

    @Test
    @DisplayName("Сортировка по году по убыванию")
    void testSortByYearDescending() {
        SortByNumericFieldStrategy strategy = new SortByNumericFieldStrategy("year", false);

        Sortable[] cars = new Sortable[]{
                createCar(100, "A", 2020),
                createCar(150, "B", 2022),
                createCar(200, "C", 2021)
        };

        strategy.sort(cars);

        assertEquals(2022, cars[0].getNumericField("year"));
        assertEquals(2021, cars[1].getNumericField("year"));
        assertEquals(2020, cars[2].getNumericField("year"));
    }

    @Test
    @DisplayName("Пустой массив")
    void testEmptyArray() {
        SortByNumericFieldStrategy strategy = new SortByNumericFieldStrategy("power", true);
        Sortable[] empty = new Sortable[0];

        assertDoesNotThrow(() -> strategy.sort(empty));
    }

    @Test
    @DisplayName("Массив из одного элемента")
    void testSingleElement() {
        SortByNumericFieldStrategy strategy = new SortByNumericFieldStrategy("power", true);
        Sortable[] cars = new Sortable[]{createCar(100, "A", 2020)};

        strategy.sort(cars);

        assertEquals(100, cars[0].getNumericField("power"));
    }

    @Test
    @DisplayName("Массив с одинаковыми значениями")
    void testEqualValues() {
        SortByNumericFieldStrategy strategy = new SortByNumericFieldStrategy("power", true);

        Sortable[] cars = new Sortable[]{
                createCar(100, "A", 2020),
                createCar(100, "B", 2021),
                createCar(100, "C", 2022)
        };

        Sortable[] original = cars.clone();
        strategy.sort(cars);

        for (Sortable car : cars) {
            assertEquals(100, car.getNumericField("power"));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "100, 200, 300, 100,200,300",
            "300, 100, 200, 100,200,300",
            "200, 300, 100, 100,200,300"
    })
    @DisplayName("Сортировка по мощности по возрастанию (разные порядки)")
    void testSortByPowerAscendingParameterized(int a, int b, int c, int e1, int e2, int e3) {
        SortByNumericFieldStrategy strategy = new SortByNumericFieldStrategy("power", true);

        Sortable[] cars = new Sortable[]{
                createCar(a, "A", 2020),
                createCar(b, "B", 2021),
                createCar(c, "C", 2022)
        };

        strategy.sort(cars);

        assertEquals(e1, cars[0].getNumericField("power"));
        assertEquals(e2, cars[1].getNumericField("power"));
        assertEquals(e3, cars[2].getNumericField("power"));
    }

    @ParameterizedTest
    @CsvSource({
            "100, 200, 300, 300,200,100",
            "300, 100, 200, 300,200,100",
            "200, 300, 100, 300,200,100"
    })
    @DisplayName("Сортировка по мощности по убыванию (разные порядки)")
    void testSortByPowerDescendingParameterized(int a, int b, int c, int e1, int e2, int e3) {
        SortByNumericFieldStrategy strategy = new SortByNumericFieldStrategy("power", false);

        Sortable[] cars = new Sortable[]{
                createCar(a, "A", 2020),
                createCar(b, "B", 2021),
                createCar(c, "C", 2022)
        };

        strategy.sort(cars);

        assertEquals(e1, cars[0].getNumericField("power"));
        assertEquals(e2, cars[1].getNumericField("power"));
        assertEquals(e3, cars[2].getNumericField("power"));
    }

    @Test
    @DisplayName("Проверка имени стратегии (по возрастанию)")
    void testGetNameAscending() {
        SortByNumericFieldStrategy strategy = new SortByNumericFieldStrategy("power", true);
        assertEquals("Сортировка по power ASC", strategy.getName());
    }

    @Test
    @DisplayName("Проверка имени стратегии (по убыванию)")
    void testGetNameDescending() {
        SortByNumericFieldStrategy strategy = new SortByNumericFieldStrategy("year", false);
        assertEquals("Сортировка по year DESC", strategy.getName());
    }


    private Car createCar(int power, String model, int year) {
        return new Car.Builder()
                .power(power)
                .model(model)
                .year(year)
                .build();
    }
}