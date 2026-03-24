package org.example;


import org.example.util.Menu;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;

class MenuTest {

    @Test
    void shouldStartAndExit() {
        String input = "0\n"; // сразу выход
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Menu menu = new Menu();
        menu.start();
    }


    @Test
    void shouldFillCollection() {
        String input = String.join("\n",
                "1", // заполнение
                "1",
                "3",
                "0"
        );

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Menu menu = new Menu();
        menu.start();
    }



    @Test
    void shouldSaveFile() {
        String input = String.join("\n",
                "1", "1", "3",
                "4", "test.txt",
                "0", "0"
        );

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Menu menu = new Menu();
        menu.start();

        assert new File("test.txt").exists();
    }


    @Test
    void shouldOverwriteFile() throws Exception {
        String filename = "test.txt";
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("old");
        }

        String input = String.join("\n",
                "1", "1", "3",
                "4", filename,
                "2", // перезапись
                "0"
        );

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Menu menu = new Menu();
        menu.start();

        String content = new String(java.nio.file.Files.readAllBytes(
                java.nio.file.Paths.get(filename)));

        assert !content.equals("old");
    }


    @Test
    void shouldCountByPower() {
        String input = String.join("\n",
                "1", "1", "5",
                "5", // count
                "1", // power
                "100",
                "0"
        );

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Menu menu = new Menu();
        menu.start();
    }


    @Test
    void shouldHandleInvalidInput() {
        String input = String.join("\n",
                "abc", // неверный ввод
                "0"
        );

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Menu menu = new Menu();
        menu.start();
    }



}

