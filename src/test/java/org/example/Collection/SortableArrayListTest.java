package org.example.Collection;

import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SortableArrayListTest {

    private Car createCar(int i) {
        return new Car.Builder()
                .power(100 + i)
                .model("Model-" + i)
                .year(2000 + i)
                .build();
    }

    @Test
    void addAndSize() {
        SortableArrayList<Car> list = new SortableArrayList<>();

        list.add(createCar(0));
        list.add(createCar(2));

        assertEquals(2, list.size());
    }

    @Test
    void getValidIndex() {
        SortableArrayList<Car> list = new SortableArrayList<>();
        Car item = createCar(5);

        list.add(item);

        assertEquals(item, list.get(0));
    }

    @Test
    void getInvalidIndexThrows() {
        SortableArrayList<Car> list = new SortableArrayList<>();

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @Test
    void getOrNull() {
        SortableArrayList<Car> list = new SortableArrayList<>();

        assertNull(list.getOrNull(0));

        Car item = createCar(10);
        list.add(item);

        assertEquals(item, list.getOrNull(0));
    }

    @Test
    void isEmptyCheck() {
        SortableArrayList<Car> list = new SortableArrayList<>();

        assertTrue(list.isEmpty());

        list.add(createCar(15));

        assertFalse(list.isEmpty());
    }

    @Test
    void toArrayCheck() {
        SortableArrayList<Car> list = new SortableArrayList<>();

        Car a = createCar(2);
        Car b = createCar(1);

        list.add(a);
        list.add(b);

        Sortable[] arr = list.toArray();

        assertEquals(2, arr.length);
        assertEquals(a, arr[0]);
        assertEquals(b, arr[1]);
    }

    @Test
    void expandArrayWorks() {
        SortableArrayList<Car> list = new SortableArrayList<>();

        for (int i = 0; i < 25; i++) {
            list.add(createCar(24));
        }

        assertEquals(25, list.size());
        assertNotNull(list.get(24));
    }

    @Test
    void constructorWithArray() {
        Car[] items = {
                createCar(4),
                createCar(20)
        };

        SortableArrayList<Car> list = new SortableArrayList<>(items);

        assertEquals(2, list.size());
        assertEquals(items[0], list.get(0));
        assertEquals(items[1], list.get(1));
    }
}
