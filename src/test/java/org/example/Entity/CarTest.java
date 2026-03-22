package org.example.Entity;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    private Car car;

    @BeforeEach
    void setUp() {
         car = new Car.Builder()
                .power(200)
                .model("Model")
                .year(2026)
                .build();
    }

    @Test
    @DisplayName("Создание автомобиля с правильными данными")
    public void testCreateCar() {
        assertNotNull(car, "Автомобиль не должен быть null");
        assertEquals(200, car.getPower(), "Мощность не совпадает");
        assertEquals("Model", car.getModel(), "Модель не совпадает");
        assertEquals(2026, car.getYear(), "Год не совпадает");
    }

    @Test
    @DisplayName("Builder возвращает this для цепочки вызовов")
    void testBuilderReturnsThis() {
        Car.Builder builder = new Car.Builder();

        assertSame(builder, builder.power(100), "power() должен возвращать this");
        assertSame(builder, builder.model("Test"), "model() должен возвращать this");
        assertSame(builder, builder.year(2020), "year() должен возвращать this");
    }

    @Test
    @DisplayName("Создание автомобиля без установки мощности")
    void testBuildWithoutPower() {
        Car.Builder builder = new Car.Builder()
                .model("Test")
                .year(2020);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(exception.getMessage().contains("Мощность должна быть положительной"));
    }

    @Test
    @DisplayName("Создание автомобиля без установки модели")
    void testBuildWithoutModel() {
        Car.Builder builder = new Car.Builder()
                .power(100)
                .year(2020);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(exception.getMessage().contains("Модель не может быть пустой"));
    }

    @Test
    @DisplayName("Создание автомобиля без установки года")
    void testBuildWithoutYear() {
        Car.Builder builder = new Car.Builder()
                .power(100)
                .model("Test");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, builder::build);
        assertTrue(exception.getMessage().contains("Некорректный год"));
    }

    @Test
    @DisplayName("Валидация мощности")
    void testValidationPower() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Car.Builder().power(0).model("Test").year(2020).build();
        }, "Мощность 0 должна вызывать исключение");

        assertThrows(IllegalArgumentException.class, () -> {
            new Car.Builder().power(-50).model("Test").year(2020).build();
        }, "Отрицательная мощность должна вызывать исключение");

        assertDoesNotThrow(() -> {
            new Car.Builder().power(1).model("Test").year(2020).build();
        }, "Положительная мощность должна проходить валидацию");
    }

    @Test
    @DisplayName("Валидация модели")
    void testValidationModel() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Car.Builder().power(100).model(null).year(2020).build();
        }, "null модель должна вызывать исключение");

        assertThrows(IllegalArgumentException.class, () -> {
            new Car.Builder().power(100).model("").year(2020).build();
        }, "Пустая строка должна вызывать исключение");

        assertThrows(IllegalArgumentException.class, () -> {
            new Car.Builder().power(100).model("   ").year(2020).build();
        }, "Строка из пробелов должна вызывать исключение");

        assertDoesNotThrow(() -> {
            new Car.Builder().power(100).model("Toyota").year(2020).build();
        }, "Непустая модель должна проходить валидацию");
    }

    @Test
    @DisplayName("Валидация года")
    void testValidationYear() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Car.Builder().power(100).model("Test").year(1885).build();
        }, "Год 1885 (меньше минимума) должен вызывать исключение");

        assertThrows(IllegalArgumentException.class, () -> {
            new Car.Builder().power(100).model("Test").year(2027).build();
        }, "Год 2027 (больше максимума) должен вызывать исключение");

        assertDoesNotThrow(() -> {
            new Car.Builder().power(100).model("Test").year(1886).build();
        }, "Год 1886 (граничное значение) должен проходить валидацию");

        assertDoesNotThrow(() -> {
            new Car.Builder().power(100).model("Test").year(2026).build();
        }, "Год 2026 (граничное значение) должен проходить валидацию");
    }

    @Test
    @DisplayName("toString() должен содержать все поля")
    void testToString() {
        String result = car.toString();

        assertTrue(result.contains("200"), "toString должен содержать мощность");
        assertTrue(result.contains("Model"), "toString должен содержать модель");
        assertTrue(result.contains("2026"), "toString должен содержать год");
    }

}
