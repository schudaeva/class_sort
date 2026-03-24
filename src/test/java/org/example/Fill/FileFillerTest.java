package org.example.Fill;

import org.example.Collection.SortableArrayList;
import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileFillerTest {

    @TempDir
    Path tempDir;
    private Path testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = tempDir.resolve("test_cars.txt");
    }

    @Test
    @DisplayName("Чтение из файла с корректными данными")
    void testFillFromValidFile() throws IOException {
        List<String> lines = List.of(
                "150,Toyota,2020",
                "200,BMW,2021",
                "100,Honda,2022"
        );
        Files.write(testFile, lines);

        FileFiller filler = new FileFiller(testFile.toString());
        SortableArrayList<Sortable> result = filler.fill(5);

        assertEquals(3, result.size());

        Car car = (Car) result.get(0);
        assertEquals(150, car.getPower());
        assertEquals("Toyota", car.getModel());
        assertEquals(2020, car.getYear());
    }

    @Test
    @DisplayName("Чтение с ограничением по количеству строк")
    void testFillWithLimit() throws IOException {
        List<String> lines = List.of(
                "150,Toyota,2020",
                "200,BMW,2021",
                "100,Honda,2022"
        );
        Files.write(testFile, lines);

        FileFiller filler = new FileFiller(testFile.toString());
        SortableArrayList<Sortable> result = filler.fill(2);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Пропуск битых строк")
    void testSkipInvalidLines() throws IOException {
        List<String> lines = List.of(
                "150,Toyota,2020",     // корректная
                "200,BMW",             // битая
                "300,Audi,2022",       // корректная
                "abc,def,ghi"          // битая
        );
        Files.write(testFile, lines);

        FileFiller filler = new FileFiller(testFile.toString());
        SortableArrayList<Sortable> result = filler.fill(10);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Пустой файл")
    void testEmptyFile() throws IOException {
        Files.createFile(testFile);

        FileFiller filler = new FileFiller(testFile.toString());
        SortableArrayList<Sortable> result = filler.fill(5);

        assertEquals(0, result.size());
    }


    @Test
    @DisplayName("Файл не существует - исключение")
    void testFileNotFound() {
        FileFiller filler = new FileFiller("nonexistent_file.txt");

        assertThrows(IllegalArgumentException.class, () -> filler.fill(5));
    }
}