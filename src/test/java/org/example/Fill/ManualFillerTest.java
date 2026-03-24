package org.example.Fill;


import org.example.Collection.SortableArrayList;
import org.example.Entity.Sortable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManualFillerTest {

    private InputStream originalIn;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
    }

    @Test
    void fillCreatesCorrectNumberOfCars() {
        String input = String.join("\n",
                "Toyota", "150", "2010",
                "BMW", "200", "2015"
        );

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ManualFiller filler = new ManualFiller();
        SortableArrayList<Sortable> result = filler.fill(2);

        assertEquals(2, result.size());
    }

    @Test
    void fillRetriesOnInvalidInput() {

        String input = String.join("\n",
                "Toyota", "bad_number",  // ошибка
                "Toyota", "150", "2010"         // корректный ввод
        );

        System.setIn(new ByteArrayInputStream(input.getBytes()));



        ManualFiller filler = new ManualFiller();
        SortableArrayList<Sortable> result = filler.fill(1);

        assertEquals(1, result.size());
    }
}
