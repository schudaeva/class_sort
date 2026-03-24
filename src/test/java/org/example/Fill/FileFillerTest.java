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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        tempFile = Files.createTempFile("test", ".txt");
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

        assertEquals(3, result.size()); // "bad" строка пропущена
    }

    @Test
    void fillRespectsLimit() {
        FileFiller filler = new FileFiller(tempFile.toString(), new TestFactory());

        SortableArrayList<Sortable> result = filler.fill(2);

        assertEquals(2, result.size());
    }

    @Test
    void fillThrowsIfFileNotFound() {
        FileFiller filler = new FileFiller("non_existent.txt", new TestFactory());

        assertThrows(IllegalArgumentException.class, () -> filler.fill(5));
    }
}
