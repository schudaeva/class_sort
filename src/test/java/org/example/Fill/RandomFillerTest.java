package org.example.Fill;


import org.example.Collection.SortableArrayList;
import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomFillerTest {

    @Test
    void fillGeneratesCorrectAmountOfCars() {
        RandomFiller filler = new RandomFiller();

        SortableArrayList<Sortable> result = filler.fill(5);

        assertEquals(5, result.size());
    }

    @Test
    void fillGeneratesValidCars() {
        RandomFiller filler = new RandomFiller();

        SortableArrayList<Sortable> result = filler.fill(10);

        for (Sortable sortable : result) {
            assertNotNull(sortable);
            assertTrue(sortable instanceof Car);

            Car car = (Car) sortable;

            assertNotNull(car.getModel());
            assertTrue(car.getPower() >= 50 && car.getPower() <= 500);
            assertTrue(car.getYear() >= 1990 && car.getYear() <= 2025);
        }
    }
}
