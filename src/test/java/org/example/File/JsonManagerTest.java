package org.example.File;

import org.example.Collection.SortableArrayList;
import org.example.Collection.SortableCollection;
import org.example.Entity.Sortable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class JsonManagerTest {

    static class TestItem implements Sortable {
        int value;

        TestItem(int value) {
            this.value = value;
        }

        @Override
        public int getNumericField(String fieldName) {
            return value;
        }

        @Override
        public String getStringField(String fieldName) {
            return String.valueOf(value);
        }

        @Override
        public String toFileString() {
            return String.valueOf(value);
        }

        @Override
        public boolean isValid() {
            return Sortable.super.isValid();
        }

        @Override
        public String[] getAvailableNumericFields() {
            return Sortable.super.getAvailableNumericFields();
        }

        @Override
        public String[] getAvailableStringFields() {
            return Sortable.super.getAvailableStringFields();
        }

        @Override
        public Sortable copy() {
            return null;
        }

        @Override
        public Object getFieldValue(String fieldName) {
            return value;
        }
    }

    static class TestCollection extends SortableArrayList<TestItem> {}

    private JsonManager manager;
    private String path;

    @BeforeEach
    void setup() {
        path = "test.json";
        manager = new JsonManager(TestItem.class, TestCollection::new, path);
        manager.clear();
    }

    @AfterEach
    void cleanup() {
        new File(path).delete();
    }

    @Test
    void saveAndLoad() {
        TestCollection collection = new TestCollection();
        collection.add(new TestItem(1));
        collection.add(new TestItem(2));

        manager.save(collection);

        SortableCollection<TestItem> loaded = manager.load();

        assertNotNull(loaded);
        assertEquals(2, loaded.size());
    }

    @Test
    void appendAddsNewCollection() {
        TestCollection c1 = new TestCollection();
        c1.add(new TestItem(1));

        TestCollection c2 = new TestCollection();
        c2.add(new TestItem(2));

        manager.append(c1);
        manager.append(c2);

        SortableCollection<TestItem>[] array = manager.loadArray();

        assertEquals(2, array.length);
    }

    @Test
    void appendValuesAddsElement() {
        TestCollection collection = new TestCollection();
        collection.add(new TestItem(1));

        manager.save(collection);
        manager.appendValues(new TestItem(5), 0);

        SortableCollection<TestItem> loaded = manager.load();

        assertEquals(2, loaded.size());
    }

    @Test
    void clearEmptiesFile() {
        TestCollection collection = new TestCollection();
        collection.add(new TestItem(1));

        manager.save(collection);
        manager.clear();

        SortableCollection<TestItem>[] array = manager.loadArray();

        assertTrue(array == null || array.length == 0);
    }

    @Test
    void loadEmptyReturnsNull() {
        manager.clear();
        SortableCollection<TestItem> loaded = manager.load();

        assertNull(loaded);
    }
}
