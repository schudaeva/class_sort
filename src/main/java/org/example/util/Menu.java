package org.example.util;

import org.example.File.FileSaver;
import org.example.Collection.SortableArrayList;
import org.example.Collection.SortableCollection;
import org.example.Entity.Sortable;
import org.example.File.JsonManager;
import org.example.Fill.*;
import org.example.Parallel.ParallelCounter;
import org.example.Sort.EvenOddSortStrategy;
import org.example.Sort.SortByNumericFieldStrategy;
import org.example.Sort.SortByStringFieldStrategy;
import org.example.Sort.SortStrategy;
import org.example.Entity.Car;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private SortableCollection<Sortable> currentCollection;
    private final List<SortStrategy> strategies;
    private final List<DataFiller> fillers;


    public Menu() {
        this.scanner = new Scanner(System.in);
        this.currentCollection = new SortableArrayList<>();
        this.strategies = new ArrayList<>();
        this.fillers = new ArrayList<>();

        registerDefaultStrategies();
        registerDefaultFillers();
    }

    private void registerDefaultStrategies() {
        strategies.add(new SortByNumericFieldStrategy("power", true));
        strategies.add(new SortByNumericFieldStrategy("power", false));
        strategies.add(new SortByStringFieldStrategy("model", true));
        strategies.add(new SortByStringFieldStrategy("model", false));
        strategies.add(new SortByNumericFieldStrategy("year", true));
        strategies.add(new SortByNumericFieldStrategy("year", false));
        strategies.add(new EvenOddSortStrategy());
    }

    private void registerDefaultFillers() {
        fillers.add(new RandomFiller());
        fillers.add(new ManualFiller());
        // FileFiller добавляется динамически с запросом имени файла
    }

    public void start() {
        while (true) {
            printMainMenu();
            int choice = readInt();

            switch (choice) {
                case 0:
                    System.out.println("Выход из программы");
                    return;
                case 1:
                    handleFill();
                    break;
                case 2:
                    handleSort();
                    break;
                case 3:
                    handleDisplay();
                    break;
                case 4:
                    handleSaveToFile();
                    break;
                case 5:
                    handleCount();
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\nМеню");
        System.out.println("1. Заполнить коллекцию");
        System.out.println("2. Отсортировать");
        System.out.println("3. Показать коллекцию");
        System.out.println("4. Сохранить в файл");
        System.out.println("5. Подсчитать вхождения");
        System.out.println("0. Выход");
        System.out.print("Выбор: ");
    }

    private void handleFill() {
        System.out.println("\nЗаполнение коллекции");

        // Показываем доступные способы заполнения
        for (int i = 0; i < fillers.size(); i++) {
            System.out.println((i + 1) + ". " + fillers.get(i).getName());
        }
        System.out.println((fillers.size() + 1) + ". Из файла");
        System.out.println((fillers.size() + 2) + ". Из json-файла");
        System.out.println("0. Вернуться в главное меню");

        System.out.print("Выберите способ: ");
        int choice = readInt();

        if (choice == 0) {
            System.out.println("Возврат в главное меню");
            return;
        }

        DataFiller filler;
        if (choice >= 1 && choice <= fillers.size()) {
            filler = fillers.get(choice - 1);
        } else if (choice == fillers.size() + 1) {
            while (true) {
                System.out.print("Введите имя файла (для выхода введите 0): ");
                String filename = scanner.nextLine().trim();

                if (filename.equals("0")) {
                    System.out.println("Возврат в главное меню");
                    return;
                }

                if (filename.isEmpty()) {
                    System.out.println("Имя файла не может быть пустым");
                    continue;
                }
                java.nio.file.Path path = java.nio.file.Paths.get(filename);
                if (java.nio.file.Files.exists(path) && !java.nio.file.Files.isDirectory(path)) {
                    filler = new FileFiller(filename);
                    break;
                } else {
                    System.out.println("Файл не найден: " + filename);
                    System.out.println("Проверьте имя файла и попробуйте снова.");
                }
            }
        } else if (choice == fillers.size() + 2) {
            while (true) {
                System.out.print("Введите имя файла (для выхода введите 0): ");
                String filename = scanner.nextLine().trim();

                if (filename.equals("0")) {
                    System.out.println("Возврат в главное меню");
                    return;
                }

                if (filename.isEmpty()) {
                    System.out.println("Имя файла не может быть пустым");
                    continue;
                }
                java.nio.file.Path path = java.nio.file.Paths.get(filename);
                if (java.nio.file.Files.exists(path) && !java.nio.file.Files.isDirectory(path)) {
                    filler = new JsonCarFiller(filename);
                    break;
                } else {
                    System.out.println("Файл не найден: " + filename);
                    System.out.println("Проверьте имя файла и попробуйте снова.");
                }
            }
        } else {
            System.out.println("Неверный выбор");
            return;
        }

        System.out.print("Количество элементов: ");
        int length = readInt();

        if (length <= 0) {
            System.out.println("Длина должна быть положительной");
            return;
        }


        SortableArrayList<Sortable> collection = filler.fill(length);
        currentCollection = collection;
        System.out.println("Добавлено " + collection.size() + " элементов");
    }

    private void handleSort() {
        if (currentCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        System.out.println("\nСортировка");

        for (int i = 0; i < strategies.size(); i++) {
            System.out.println((i + 1) + ". " + strategies.get(i).getName());
        }
        System.out.println("0. Вернуться в главное меню");

        System.out.print("Выберите стратегию: ");
        int choice = readInt();
        if (choice == 0) {
            System.out.println("Возврат в главное меню");
            return;
        }

        if (choice < 1 || choice > strategies.size()) {
            System.out.println("Неверный выбор");
            return;
        }

        SortStrategy strategy = strategies.get(choice - 1);
        Sortable[] array = currentCollection.toArray();
        strategy.sort(array);
        currentCollection = new SortableArrayList<>(array);

        System.out.println("Сортировка выполнена: " + strategy.getName());
    }

    private void handleDisplay() {
        if (currentCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        System.out.println("\nСодержимое коллекции (" + currentCollection.size() + " элементов)");
        int i = 0;
        for (Sortable item : currentCollection) {
            System.out.printf("%3d: %s%n", ++i, item.toString());
        }
    }

    private void handleSaveToFile() {
        if (currentCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        // Выбор формата сохранения
        System.out.println("\nВыберите формат сохранения:");
        System.out.println("1. TXT");
        System.out.println("2. JSON");
        System.out.println("0. Вернуться в главное меню");
        System.out.print("Выбор: ");

        int formatChoice = readInt();
        if (formatChoice == 0) {
            System.out.println("Возврат в главное меню");
            return;
        }
        if (formatChoice != 1 && formatChoice != 2) {
            System.out.println("Неверный выбор. Сохранение отменено.");
            return;
        }

        boolean isJson = (formatChoice == 2);

        System.out.print("Введите имя файла (для выхода введите 0): ");
        String filename = scanner.nextLine().trim();
        if (filename.equals("0")) {
            System.out.println("Возврат в главное меню");
            return;
        }
        if (filename.isEmpty()) {
            System.out.println("Имя файла не может быть пустым");
            return;
        }

        // Добавляем расширение, если его нет
        if (isJson && !filename.toLowerCase().endsWith(".json")) {
            filename = filename + ".json";
        } else if (!isJson && !filename.toLowerCase().endsWith(".txt")) {
            filename = filename + ".txt";
        }

        boolean fileExists = java.nio.file.Files.exists(java.nio.file.Paths.get(filename));

        String mode;
        if (!fileExists) {
            mode = "создание";
            System.out.println("Файл не найден. Будет создан новый файл.");
        } else {
            System.out.println("Файл уже существует. Выберите режим сохранения:");
            System.out.println("1. Добавить в конец файла");
            System.out.println("2. Перезаписать файл");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Выбор: ");

            int choice = readInt();
            if (choice == 0) {
                System.out.println("Возврат в главное меню.");
                return;
            } else if (choice == 1) {
                mode = "добавление";
            } else if (choice == 2) {
                mode = "перезапись";
            } else {
                System.out.println("Неверный выбор. Сохранение отменено.");
                return;
            }
        }

        boolean success;
        String formatName = isJson ? "JSON" : "TXT";

        if (isJson) {
            // Сохранение через JsonManager
            JsonManager jsonManager = new JsonManager(Car.class, SortableArrayList::new, filename);

            if (mode.equals("перезапись") || (!fileExists)) {
                jsonManager.save(currentCollection);
            } else {
                jsonManager.append(currentCollection);
            }
            success = true; // JsonManager выбрасывает исключения при ошибках, поэтому если дошли сюда - успех
            System.out.println("Коллекция сохранена в файл: " + filename + " (формат: " + formatName + ", режим: " + mode + ")");
        } else {
            // Сохранение через FileSaver
            FileSaver fileSaver = new FileSaver();

            if (mode.equals("перезапись") || (!fileExists)) {
                success = fileSaver.writeValues(filename, currentCollection);
            } else {
                success = fileSaver.append(filename, currentCollection);
            }

            if (success) {
                System.out.println("Коллекция сохранена в файл: " + filename + " (формат: " + formatName + ", режим: " + mode + ")");
            } else {
                System.out.println("Ошибка при сохранении в файл: " + filename);
            }
        }
    }

    private void handleCount() {
        if (currentCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return;
        }

        System.out.println("\nПодсчет вхождений");
        System.out.println("Поля для подсчета:");
        System.out.println("1. По мощности");
        System.out.println("2. По модели");
        System.out.println("3. По году");
        System.out.println("0. Вернуться в главное меню");
        System.out.print("Выберите поле:  ");

        int choice = readInt();

        switch (choice) {
            case 0:
                System.out.println("Возврат в главное меню.");
                break;
            case 1:
                System.out.print("Искомая мощность: ");
                int power = readInt();
                if (power <= 0) {
                    System.out.println("Мощность должна быть положительной");
                    break;
                }
                long powerCount = ParallelCounter.countNumeric(currentCollection, "power", power);
                System.out.println("Найдено автомобилей с мощностью " + power + ": " + powerCount);
                break;
            case 2:
                System.out.print("Искомая модель: ");
                String model = scanner.nextLine();
                if (model == null || model.trim().isEmpty()) {
                    System.out.println("Модель не может быть пустой");
                    break;
                }
                long modelCount = ParallelCounter.countString(currentCollection, "model", model);
                System.out.println("Найдено автомобилей модели " + model + ": " + modelCount);
                break;
            case 3:
                System.out.print("Искомый год: ");
                int year = readInt();
                if (year < 1886 || year > 2026) {
                    System.out.println("Некорректный год");
                    break;
                }
                long yearCount = ParallelCounter.countNumeric(currentCollection, "year", year);
                System.out.println("Найдено автомобилей " + year + " года: " + yearCount);
                break;
            default:
                System.out.println("Неверный выбор");
        }
    }

    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}