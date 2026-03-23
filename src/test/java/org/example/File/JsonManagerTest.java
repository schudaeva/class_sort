package org.example.File;

import org.example.Collection.SortableArrayList;
import org.example.Collection.SortableCollection;
import org.example.Entity.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JsonManagerTest {

    @TempDir
    Path tempDir;

    private Car createCar(int i) {
        return new Car.Builder()
                .power(100 + i)
                .model("Model-" + i)
                .year(2000 + i)
                .build();
    }

    private SortableArrayList<Car> createCollection(int count) {
        SortableArrayList<Car> list = new SortableArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(createCar(i));
        }
        return list;
    }

    @Test
    void append_shouldCreateFile_ifNotExists() {
        File file = tempDir.resolve("test.json").toFile();

        JsonManager manager = new JsonManager(
                Car.class,
                SortableArrayList::new,
                file.getAbsolutePath()
        );

        manager.append(createCollection(1));

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void append_shouldAddMultipleCollections() {
        File file = tempDir.resolve("test.json").toFile();

        JsonManager manager = new JsonManager(
                Car.class,
                SortableArrayList::new,
                file.getAbsolutePath()
        );

        manager.append(createCollection(1));
        manager.append(createCollection(2));

        SortableCollection<Car>[] result = manager.loadArray();

        assertEquals(2, result.length);
        assertEquals(1, result[0].toArray().length);
        assertEquals(2, result[1].toArray().length);
    }

    @Test
    void clear_shouldEmptyFile() throws Exception {
        File file = tempDir.resolve("test.json").toFile();

        JsonManager manager = new JsonManager(
                Car.class,
                SortableArrayList::new,
                file.getAbsolutePath()
        );

        manager.append(createCollection(2));
        manager.clear();

        String content = Files.readString(file.toPath());
        assertEquals("[]", content);
    }

    @Test
    void save_shouldOverwriteData() {
        File file = tempDir.resolve("test.json").toFile();

        JsonManager manager = new JsonManager(
                Car.class,
                SortableArrayList::new,
                file.getAbsolutePath()
        );

        manager.append(createCollection(3));
        manager.save(createCollection(1));

        SortableCollection<Car>[] result = manager.loadArray();

        assertEquals(1, result.length);
        assertEquals(1, result[0].toArray().length);
    }

    @Test
    void load_shouldReturnFirstCollection() {
        File file = tempDir.resolve("test.json").toFile();

        JsonManager manager = new JsonManager(
                Car.class,
                SortableArrayList::new,
                file.getAbsolutePath()
        );

        manager.append(createCollection(5));

        SortableCollection<Car> collection = manager.load();

        assertNotNull(collection);
        assertEquals(5, collection.toArray().length);
    }

    @Test
    void load_shouldReturnNull_ifEmpty() {
        File file = tempDir.resolve("test.json").toFile();
        JsonManager manager = new JsonManager(
                Car.class,
                SortableArrayList::new,
                file.getAbsolutePath()
        );

        manager.clear();

        SortableCollection<Car> collection = manager.load();

        assertNull(collection);
    }
}
