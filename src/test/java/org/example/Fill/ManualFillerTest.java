package org.example.Fill;

import org.example.Collection.SortableArrayList;
import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

class ManualFillerTest {

    private static final String LINE_SEPARATOR = System.lineSeparator();


    private ManualFiller createManualFillerWithInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        return new ManualFiller();
    }

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
    }

    @Test
    @DisplayName("Успешный ввод корректных данных")
    void testFillWithValidInput() {
        String input = "Toyota" + LINE_SEPARATOR +
                "150" + LINE_SEPARATOR +
                "2020" + LINE_SEPARATOR;

        ManualFiller filler = createManualFillerWithInput(input);
        SortableArrayList<Sortable> result = filler.fill(1);

        assertEquals(1, result.size());
        Car car = (Car) result.get(0);
        assertEquals("Toyota", car.getModel());
        assertEquals(150, car.getPower());
        assertEquals(2020, car.getYear());
    }

    @Test
    @DisplayName("Ввод нескольких автомобилей")
    void testFillMultipleCars() {
        String input = "Toyota" + LINE_SEPARATOR +
                "150" + LINE_SEPARATOR +
                "2020" + LINE_SEPARATOR +
                "BMW" + LINE_SEPARATOR +
                "200" + LINE_SEPARATOR +
                "2021" + LINE_SEPARATOR;

        ManualFiller filler = createManualFillerWithInput(input);
        SortableArrayList<Sortable> result = filler.fill(2);

        assertEquals(2, result.size());

        Car first = (Car) result.get(0);
        assertEquals("Toyota", first.getModel());
        assertEquals(150, first.getPower());
        assertEquals(2020, first.getYear());

        Car second = (Car) result.get(1);
        assertEquals("BMW", second.getModel());
        assertEquals(200, second.getPower());
        assertEquals(2021, second.getYear());
    }


    @Test
    @DisplayName("Неверный ввод года (буквы) - повторный запрос всех полей")
    void testInvalidYearThenValid() {

        String input = "Toyota" + LINE_SEPARATOR +
                "150" + LINE_SEPARATOR +
                "abc" + LINE_SEPARATOR +      // неверно
                "Honda" + LINE_SEPARATOR +    // повторный ввод модели
                "200" + LINE_SEPARATOR +      // мощность
                "2020" + LINE_SEPARATOR;      // верный год

        ManualFiller filler = createManualFillerWithInput(input);
        SortableArrayList<Sortable> result = filler.fill(1);

        assertEquals(1, result.size());
        Car car = (Car) result.get(0);
        assertEquals("Honda", car.getModel());
        assertEquals(200, car.getPower());
        assertEquals(2020, car.getYear());
    }

    @Test
    @DisplayName("Отрицательная мощность - повторный запрос всех полей")
    void testNegativePowerThenValid() {

        String input = "Toyota" + LINE_SEPARATOR +
                "-50" + LINE_SEPARATOR +      // неверно (отрицательная)
                "2020" + LINE_SEPARATOR +     // год (будет пропущен после ошибки)
                "Honda" + LINE_SEPARATOR +    // повторный ввод модели
                "150" + LINE_SEPARATOR +      // верная мощность
                "2021" + LINE_SEPARATOR;      // верный год

        ManualFiller filler = createManualFillerWithInput(input);
        SortableArrayList<Sortable> result = filler.fill(1);

        assertEquals(1, result.size());
        Car car = (Car) result.get(0);
        assertEquals("Honda", car.getModel());
        assertEquals(150, car.getPower());
        assertEquals(2021, car.getYear());
    }

    @Test
    @DisplayName("Пустая модель - повторный запрос всех полей")
    void testEmptyModelThenValid() {

        String input = "" + LINE_SEPARATOR +          // пустая модель (ошибка)
                "150" + LINE_SEPARATOR +       // мощность (будет пропущена после ошибки)
                "2020" + LINE_SEPARATOR +      // год (будет пропущен после ошибки)
                "Toyota" + LINE_SEPARATOR +    // повторный ввод модели
                "150" + LINE_SEPARATOR +       // мощность
                "2020" + LINE_SEPARATOR;       // год

        ManualFiller filler = createManualFillerWithInput(input);
        SortableArrayList<Sortable> result = filler.fill(1);

        assertEquals(1, result.size());
        Car car = (Car) result.get(0);
        assertEquals("Toyota", car.getModel());
        assertEquals(150, car.getPower());
        assertEquals(2020, car.getYear());
    }

    @Test
    @DisplayName("Проверка имени стратегии")
    void testGetName() {
        ManualFiller filler = new ManualFiller();
        assertEquals("Заполнение вручную", filler.getName());
    }
}