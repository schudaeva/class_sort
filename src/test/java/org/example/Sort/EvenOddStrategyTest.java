package org.example.Sort;

import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class EvenOddSortStrategyTest {

    private EvenOddSortStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new EvenOddSortStrategy();
    }

    @Test
    @DisplayName("Сортировка четных значений, нечетные остаются на местах")
    void testSortEvenNumbers() {
        Sortable[] cars = new Sortable[]{
                createCar(5, "A", 2020),  // нечет
                createCar(4, "B", 2021),  // чет
                createCar(3, "C", 2022),  // нечет
                createCar(2, "D", 2023)   // чет
        };

        strategy.sort(cars);

        assertEquals(5, cars[0].getNumericField("power"));
        assertEquals(2, cars[1].getNumericField("power"));
        assertEquals(3, cars[2].getNumericField("power"));
        assertEquals(4, cars[3].getNumericField("power"));
    }

    @Test
    @DisplayName("Все значения четные - полная сортировка")
    void testAllEvenNumbers() {
        Sortable[] cars = new Sortable[]{
                createCar(8, "A", 2020),
                createCar(2, "B", 2021),
                createCar(6, "C", 2022),
                createCar(4, "D", 2023)
        };

        strategy.sort(cars);

        assertEquals(2, cars[0].getNumericField("power"));
        assertEquals(4, cars[1].getNumericField("power"));
        assertEquals(6, cars[2].getNumericField("power"));
        assertEquals(8, cars[3].getNumericField("power"));
    }

    @Test
    @DisplayName("Все значения нечетные - без изменений")
    void testAllOddNumbers() {
        Sortable[] cars = new Sortable[]{
                createCar(5, "A", 2020),
                createCar(3, "B", 2021),
                createCar(7, "C", 2022),
                createCar(1, "D", 2023)
        };

        Sortable[] original = cars.clone();
        strategy.sort(cars);

        for (int i = 0; i < cars.length; i++) {
            assertEquals(original[i].getNumericField("power"),
                    cars[i].getNumericField("power"));
        }
    }


    @Test
    @DisplayName("Пустой массив")
    void testEmptyArray() {
        Sortable[] empty = new Sortable[0];
        assertDoesNotThrow(() -> strategy.sort(empty));
    }

    @Test
    @DisplayName("Массив из одного элемента")
    void testSingleElement() {
        Sortable[] cars = new Sortable[]{createCar(4, "A", 2020)};
        strategy.sort(cars);
        assertEquals(4, cars[0].getNumericField("power"));
    }

    @Test
    @DisplayName("Массив с null элементами")
    void testArrayWithNulls() {
        Sortable[] cars = new Sortable[]{
                createCar(4, "A", 2020),
                null,
                createCar(2, "B", 2021),
                null,
                createCar(6, "C", 2022)
        };

        strategy.sort(cars);

        assertEquals(2, cars[0].getNumericField("power"));
        assertNull(cars[1]);
        assertEquals(4, cars[2].getNumericField("power"));
        assertNull(cars[3]);
        assertEquals(6, cars[4].getNumericField("power"));
    }

    @Test
    @DisplayName("Сортировка четных по году")
    void testSortEvenByYear() {
        EvenOddSortStrategy yearStrategy = new EvenOddSortStrategy("year");

        Sortable[] cars = new Sortable[]{
                createCar(100, "A", 2021), // нечетный год
                createCar(150, "B", 2024), // четный год
                createCar(200, "C", 2022), // четный год
                createCar(250, "D", 2023)  // нечетный год
        };

        yearStrategy.sort(cars);

        assertEquals(2021, cars[0].getNumericField("year"));
        assertEquals(2022, cars[1].getNumericField("year"));
        assertEquals(2024, cars[2].getNumericField("year"));
        assertEquals(2023, cars[3].getNumericField("year"));
    }

    @ParameterizedTest
    @CsvSource({
            "4, 2, 6, 2,4,6",
            "10, 8, 4, 4,8,10",
            "8, 6, 4, 4,6,8"
    })
    @DisplayName("Сортировка четных значений")
    void testSortEvenNumbersParameterized(int a, int b, int c, int e1, int e2, int e3) {
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

    private Car createCar(int power, String model, int year) {
        return new Car.Builder()
                .power(power)
                .model(model)
                .year(year)
                .build();
    }
}