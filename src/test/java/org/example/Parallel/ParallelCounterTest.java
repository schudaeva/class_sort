package org.example.Parallel;

import org.example.Collection.SortableArrayList;
import org.example.Collection.SortableCollection;
import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ParallelCounterTest {

    private Sortable[] testArray;
    private SortableCollection<Sortable> testCollection;

    @BeforeEach
    void setUp() {
        // Создаем тестовые данные через Builder
        testArray = new Sortable[]{
                new Car.Builder().power(100).model("Toyota").year(2020).build(),
                new Car.Builder().power(150).model("BMW").year(2021).build(),
                new Car.Builder().power(100).model("Toyota").year(2022).build(),
                new Car.Builder().power(200).model("Audi").year(2023).build(),
                new Car.Builder().power(100).model("Honda").year(2020).build(),
                new Car.Builder().power(150).model("BMW").year(2021).build(),
                new Car.Builder().power(300).model("Tesla").year(2024).build(),
                new Car.Builder().power(100).model("Toyota").year(2020).build()
        };

        testCollection = new SortableArrayList<>(testArray);
    }


    @Test
    @DisplayName("countNumeric - успешный поиск в массиве")
    void testCountNumericArrayFound() {
        long result = ParallelCounter.countNumeric(testArray, "power", 100);
        assertEquals(4, result, "Должно быть 4 автомобиля с мощностью 100");

        result = ParallelCounter.countNumeric(testArray, "year", 2020);
        assertEquals(3, result, "Должно быть 3 автомобиля 2020 года");

        result = ParallelCounter.countNumeric(testArray, "power", 150);
        assertEquals(2, result, "Должно быть 2 автомобиля с мощностью 150");
    }

    @Test
    @DisplayName("countNumeric - нет совпадений в массиве")
    void testCountNumericArrayNotFound() {
        long result = ParallelCounter.countNumeric(testArray, "power", 999);
        assertEquals(0, result);

        result = ParallelCounter.countNumeric(testArray, "year", 1990);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("countNumeric - пустой массив")
    void testCountNumericEmptyArray() {
        Sortable[] empty = new Sortable[0];
        long result = ParallelCounter.countNumeric(empty, "power", 100);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("countNumeric - массив с null элементами")
    void testCountNumericArrayWithNulls() {
        Sortable[] withNulls = new Sortable[]{
                new Car.Builder().power(100).model("Toyota").year(2020).build(),
                null,
                new Car.Builder().power(100).model("BMW").year(2021).build(),
                null,
                new Car.Builder().power(100).model("Honda").year(2022).build()
        };

        long result = ParallelCounter.countNumeric(withNulls, "power", 100);
        assertEquals(3, result, "null элементы должны игнорироваться");
    }

    @Test
    @DisplayName("countNumeric - массив из одного элемента")
    void testCountNumericSingleElement() {
        Sortable[] single = new Sortable[]{
                new Car.Builder().power(100).model("Toyota").year(2020).build()
        };

        long result = ParallelCounter.countNumeric(single, "power", 100);
        assertEquals(1, result);

        result = ParallelCounter.countNumeric(single, "power", 200);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("countNumeric - все элементы совпадают")
    void testCountNumericAllMatch() {
        Sortable[] allSame = new Sortable[]{
                new Car.Builder().power(100).model("A").year(2020).build(),
                new Car.Builder().power(100).model("B").year(2020).build(),
                new Car.Builder().power(100).model("C").year(2020).build()
        };

        long result = ParallelCounter.countNumeric(allSame, "power", 100);
        assertEquals(3, result);
    }

    @Test
    @DisplayName("countNumeric - с указанием количества потоков")
    void testCountNumericWithThreadCount() {
        long result = ParallelCounter.countNumeric(testArray, "power", 100, 2);
        assertEquals(4, result, "С 2 потоками должно найти 4 автомобиля");

        result = ParallelCounter.countNumeric(testArray, "power", 100, 8);
        assertEquals(4, result, "С 8 потоками должно найти 4 автомобиля");

        result = ParallelCounter.countNumeric(testArray, "power", 100, 0);
        assertEquals(4, result, "0 потоков = использовать значение по умолчанию");
    }

    @Test
    @DisplayName("countString - успешный поиск в массиве")
    void testCountStringArrayFound() {
        long result = ParallelCounter.countString(testArray, "model", "Toyota");
        assertEquals(3, result, "Должно быть 3 автомобиля Toyota");

        result = ParallelCounter.countString(testArray, "model", "BMW");
        assertEquals(2, result, "Должно быть 2 автомобиля BMW");
    }

    @Test
    @DisplayName("countString - нет совпадений в массиве")
    void testCountStringArrayNotFound() {
        long result = ParallelCounter.countString(testArray, "model", "Ferrari");
        assertEquals(0, result);
    }

    @Test
    @DisplayName("countString - регистрозависимость")
    void testCountStringCaseSensitive() {
        long result = ParallelCounter.countString(testArray, "model", "toyota");
        assertEquals(0, result, "Поиск должен быть регистрозависимым");
    }

    @Test
    @DisplayName("countString - null target")
    void testCountStringNullTarget() {
        long result = ParallelCounter.countString(testArray, "model", null);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("countString - с указанием количества потоков")
    void testCountStringWithThreadCount() {
        long result = ParallelCounter.countString(testArray, "model", "Toyota", 2);
        assertEquals(3, result);

        result = ParallelCounter.countString(testArray, "model", "Toyota", 8);
        assertEquals(3, result);
    }

    @Test
    @DisplayName("countNumeric - успешный поиск в коллекции")
    void testCountNumericCollectionFound() {
        long result = ParallelCounter.countNumeric(testCollection, "power", 100);
        assertEquals(4, result);

        result = ParallelCounter.countNumeric(testCollection, "year", 2020);
        assertEquals(3, result);
    }

    @Test
    @DisplayName("countNumeric - нет совпадений в коллекции")
    void testCountNumericCollectionNotFound() {
        long result = ParallelCounter.countNumeric(testCollection, "power", 999);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("countNumeric - пустая коллекция")
    void testCountNumericEmptyCollection() {
        SortableCollection<Sortable> empty = new SortableArrayList<>();
        long result = ParallelCounter.countNumeric(empty, "power", 100);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("countNumeric - null коллекция")
    void testCountNumericNullCollection() {
        long result = ParallelCounter.countNumeric((SortableCollection<Sortable>) null, "power", 100);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("countNumeric - коллекция с указанием потоков")
    void testCountNumericCollectionWithThreadCount() {
        long result = ParallelCounter.countNumeric(testCollection, "power", 100, 2);
        assertEquals(4, result);

        result = ParallelCounter.countNumeric(testCollection, "power", 100, 4);
        assertEquals(4, result);
    }

    @Test
    @DisplayName("countString - успешный поиск в коллекции")
    void testCountStringCollectionFound() {
        long result = ParallelCounter.countString(testCollection, "model", "Toyota");
        assertEquals(3, result);

        result = ParallelCounter.countString(testCollection, "model", "BMW");
        assertEquals(2, result);
    }

    @Test
    @DisplayName("countString - нет совпадений в коллекции")
    void testCountStringCollectionNotFound() {
        long result = ParallelCounter.countString(testCollection, "model", "Ferrari");
        assertEquals(0, result);
    }

    @Test
    @DisplayName("countString - null target в коллекции")
    void testCountStringCollectionNullTarget() {
        long result = ParallelCounter.countString(testCollection, "model", null);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("countString - коллекция с указанием потоков")
    void testCountStringCollectionWithThreadCount() {
        long result = ParallelCounter.countString(testCollection, "model", "Toyota", 2);
        assertEquals(3, result);

        result = ParallelCounter.countString(testCollection, "model", "Toyota", 8);
        assertEquals(3, result);
    }

    @ParameterizedTest
    @CsvSource({
            "power, 100, 4",
            "power, 150, 2",
            "power, 200, 1",
            "year, 2020, 3",
            "year, 2021, 2",
            "year, 2024, 1"
    })
    @DisplayName("Параметризованный тест для countNumeric (массив)")
    void testCountNumericParameterized(String field, int target, int expected) {
        long result = ParallelCounter.countNumeric(testArray, field, target);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "Toyota, 3",
            "BMW, 2",
            "Audi, 1",
            "Honda, 1",
            "Tesla, 1"
    })
    @DisplayName("Параметризованный тест для countString (массив)")
    void testCountStringParameterized(String model, int expected) {
        long result = ParallelCounter.countString(testArray, "model", model);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    @DisplayName("Разное количество потоков (массив)")
    void testDifferentThreadCounts(int threadCount) {
        long result = ParallelCounter.countNumeric(testArray, "power", 100, threadCount);
        assertEquals(4, result, "С " + threadCount + " потоками должно найти 4 автомобиля");
    }

    @Test
    @DisplayName("Несуществующее поле")
    void testInvalidField() {
        long result = ParallelCounter.countNumeric(testArray, "invalidField", 100);
        assertEquals(0, result);

        result = ParallelCounter.countString(testArray, "invalidField", "test");
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Отрицательное количество потоков")
    void testNegativeThreadCount() {
        long result = ParallelCounter.countNumeric(testArray, "power", 100, -1);
        assertEquals(4, result, "Отрицательное количество потоков должно использовать значение по умолчанию");

        result = ParallelCounter.countNumeric(testArray, "power", 100, -5);
        assertEquals(4, result);
    }

    @Test
    @DisplayName("Количество потоков больше, чем элементов")
    void testMoreThreadsThanElements() {
        Sortable[] small = new Sortable[]{
                new Car.Builder().power(100).model("A").year(2020).build()
        };

        long result = ParallelCounter.countNumeric(small, "power", 100, 10);
        assertEquals(1, result, "Потоков не может быть больше, чем элементов");
    }
}

