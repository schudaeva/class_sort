package org.example.Fill;

import org.example.Collection.SortableArrayList;
import org.example.Entity.Sortable;
import org.example.util.SortableFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileFillerTest {

    private Path tempFile;

    static class TestSortable implements Sortable {
        String value;

        TestSortable(String value) {
            this.value = value;
        }

        @Override
        public int getNumericField(String fieldName) { return 0; }

        @Override
        public String getStringField(String fieldName) { return value; }

        @Override
        public Object getFieldValue(String fieldName) { return value; }

        @Override
        public String toFileString() { return value; }
    }

    static class TestFactory implements SortableFactory {
        @Override
        public Sortable createFromString(String line) {
            if (line.equals("bad")) {
                throw new IllegalArgumentException("bad line");
            }
            return new TestSortable(line);
        }
    }

    @BeforeEach
    void setup() throws IOException {
        tempFile = Files.createTempFile("test", ".json");
        Files.write(tempFile, java.util.List.of("a", "b", "bad", "c"));
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void fillReadsAndParsesCorrectly() {
        FileFiller filler = new FileFiller(tempFile.toString(), new TestFactory());

        SortableArrayList<Sortable> result = filler.fill(10);

        assertEquals(3, result.size());
    }

    @Test
    void fillRespectsLimit() {
        FileFiller filler = new FileFiller(tempFile.toString(), new TestFactory());

        SortableArrayList<Sortable> result = filler.fill(2);

        assertEquals(2, result.size());
    }

    @Test
    void fillThrowsIfFileNotFound() {
        FileFiller filler = new FileFiller("non_existent.json", new TestFactory());

        assertThrows(IllegalArgumentException.class, () -> filler.fill(5));
    }
}
/*

import org.example.Collection.SortableArrayList;
import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.example.File.JsonManager;
import org.example.util.CarFactory;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileFillerTest {

    private static final String TEST_FILE = "test_cars.json";

    private void writeTestData(List<Car> cars) throws IOException {
        JsonManager jsonManager = new JsonManager(Car.class, SortableArrayList::new, TEST_FILE);
        try (FileWriter writer = new FileWriter(TEST_FILE)) {
            writer.write(jsonManager.toJson(cars));
        }
    }

    @Test
    void fillReturnsCorrectSizeWhenEnoughData() throws IOException {
        writeTestData(List.of(
                new Car("Toyota", 150, 2010),
                new Car("BMW", 200, 2015),
                new Car("Audi", 180, 2012)
        ));

        FileFiller filler = new FileFiller(TEST_FILE);
        SortableArrayList<Sortable> result = filler.fill(2);

        assertEquals(2, result.size());
    }

    @Test
    void fillReturnsAllIfLessThanRequested() throws IOException {
        writeTestData(List.of(
                new Car("Toyota", 150, 2010)
        ));

        FileFiller filler = new FileFiller(TEST_FILE);
        SortableArrayList<Sortable> result = filler.fill(5);

        assertEquals(1, result.size());
    }

    @Test
    void fillReturnsEmptyWhenFileEmpty() throws IOException {
        writeTestData(List.of());

        FileFiller filler = new FileFiller(TEST_FILE);
        SortableArrayList<Sortable> result = filler.fill(3);

        assertTrue(result.isEmpty());
    }

    @Test
    void fillLoadsCorrectObjects() throws IOException {
        writeTestData(List.of(
                new Car("Toyota", 150, 2010)
        ));

        FileFiller filler = new FileFiller(TEST_FILE);
        SortableArrayList<Sortable> result = filler.fill(1);

        assertEquals(1, result.size());
        assertTrue(result.get(0) instanceof Car);

        Car car = (Car) result.get(0);
        assertEquals("Toyota", car.getModel());
        assertEquals(150, car.getPower());
        assertEquals(2010, car.getYear());
    }
}*/
