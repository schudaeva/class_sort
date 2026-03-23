package org.example.Parallel;

import org.example.Entity.Sortable;

import java.util.ArrayList;
import java.util.List;

public class ParallelCounter {
    private static final int DEFAULT_THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public static long countNumeric(Sortable[] items, String fieldName, int target) {
        return countNumeric(items, fieldName, target, DEFAULT_THREAD_COUNT);
    }

    public static long countNumeric(Sortable[] items, String fieldName, int target, int threadCount) {
        if (items == null || items.length == 0) {
            return 0;
        }
        if (threadCount <= 0) {
            threadCount = DEFAULT_THREAD_COUNT;
        }
        threadCount = Math.min(threadCount, items.length);
        int chunkSize = (int) Math.ceil((double) items.length / threadCount);

        List<NumericCountThread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, items.length);

            if (start < items.length) {
                NumericCountThread thread = new NumericCountThread(
                        items, start, end, fieldName, target
                );
                thread.start();
                threads.add(thread);
            }
        }

        long total = 0;
        for (NumericCountThread thread : threads) {
            try {
                thread.join();
                total += thread.getCount();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Прерывание потока при подсчете: " + e.getMessage());
            }
        }

        return total;
    }

    public static long countString(Sortable[] items, String fieldName, String target) {
        return countString(items, fieldName, target, DEFAULT_THREAD_COUNT);
    }

    public static long countString(Sortable[] items, String fieldName, String target, int threadCount) {
        if (items == null || items.length == 0 || target == null) {
            return 0;
        }

        threadCount = Math.min(threadCount, items.length);
        int chunkSize = (int) Math.ceil((double) items.length / threadCount);

        List<StringCountThread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, items.length);

            if (start < items.length) {
                StringCountThread thread = new StringCountThread(
                        items, start, end, fieldName, target
                );
                thread.start();
                threads.add(thread);
            }
        }
        long total = 0;
        for (StringCountThread thread : threads) {
            try {
                thread.join();
                total += thread.getCount();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Прерывание потока при подсчете: " + e.getMessage());
            }
        }

        return total;
    }

    private static class NumericCountThread extends Thread {
        private final Sortable[] items;
        private final int start;
        private final int end;
        private final String fieldName;
        private final int target;
        private long count = 0;

        public NumericCountThread(Sortable[] items, int start, int end,
                                  String fieldName, int target) {
            this.items = items;
            this.start = start;
            this.end = end;
            this.fieldName = fieldName;
            this.target = target;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                Sortable item = items[i];

                // Проверка на null
                if (item == null) {
                    continue;
                }

                // Проверка на валидность объекта
                if (!item.isValid()) {
                    continue;
                }
                if (items[i] != null) {
                    try {
                        if (items[i].getNumericField(fieldName) == target) {
                            count++;
                        }
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
        }

        public long getCount() {
            return count;
        }
    }

    private static class StringCountThread extends Thread {
        private final Sortable[] items;
        private final int start;
        private final int end;
        private final String fieldName;
        private final String target;
        private long count = 0;

        public StringCountThread(Sortable[] items, int start, int end,
                                 String fieldName, String target) {
            this.items = items;
            this.start = start;
            this.end = end;
            this.fieldName = fieldName;
            this.target = target;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                if (items[i] != null && target != null) {
                    Sortable item = items[i];

                    // Проверка на null
                    if (item == null) {
                        continue;
                    }

                    // Проверка на валидность объекта
                    if (!item.isValid()) {
                        continue;
                    }
                    try {
                        String value = items[i].getStringField(fieldName);
                        if (target.equals(value)) {
                            count++;
                        }
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
        }

        public long getCount() {
            return count;
        }
    }
}
