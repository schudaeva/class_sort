package org.example.Fill;


import org.example.Collection.SortableArrayList;
import org.example.Collection.SortableCollection;
import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.example.File.JsonManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonCarFillerTest {

    private static final String TEST_FILE = "test_cars.json";

    private void writeTestData(SortableCollection<Sortable> cars) throws IOException {
        JsonManager jsonManager = new JsonManager(Car.class, SortableArrayList::new, TEST_FILE);
//        SortableCollection<Sortable>[] array = new SortableCollection[0];
//        array[0] = cars;
        jsonManager.append(cars);
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @Test
    void fillLimitsByLength() throws IOException {
        SortableCollection<Sortable> cars = new SortableArrayList<>();
        cars.add(new Car.Builder().model("Toyota").power(150).year(2010).build());
        cars.add(new Car.Builder().model("BMW").power(200).year(2015).build());
        cars.add(new Car.Builder().model("Audi").power(170).year(2012).build());

        writeTestData(cars);

        JsonCarFiller filler = new JsonCarFiller(TEST_FILE);
        SortableArrayList<Sortable> result = filler.fill(2);

        assertEquals(2, result.size());
    }

    @Test
    void fillReturnsAllIfLessData() throws IOException {
        SortableCollection<Sortable> cars = new SortableArrayList<>();
        cars.add(new Car.Builder().model("Toyota").power(150).year(2010).build());


        writeTestData(cars);

        JsonCarFiller filler = new JsonCarFiller(TEST_FILE);
        SortableArrayList<Sortable> result = filler.fill(5);

        assertEquals(1, result.size());
    }

    @Test
    void fillReturnsEmptyWhenNoData() throws IOException {
        SortableCollection<Sortable> cars = new SortableArrayList<>();
        writeTestData(cars);

        JsonCarFiller filler = new JsonCarFiller(TEST_FILE);
        SortableArrayList<Sortable> result = filler.fill(3);

        assertTrue(result.isEmpty());
    }

    @Test
    void fillLoadsCorrectObjects() throws IOException {
        SortableCollection<Sortable> cars = new SortableArrayList<>();
        cars.add(new Car.Builder().model("Toyota").power(150).year(2010).build());

        writeTestData(cars);

        JsonCarFiller filler = new JsonCarFiller(TEST_FILE);
        SortableArrayList<Sortable> result = filler.fill(1);

        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Car);

        Car car = (Car) result.get(0);
        assertEquals("Toyota", car.getModel());
        assertEquals(150, car.getPower());
        assertEquals(2010, car.getYear());
    }
}
