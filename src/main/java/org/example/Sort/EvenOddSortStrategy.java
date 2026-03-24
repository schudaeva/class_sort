package org.example.Sort;
import org.example.Entity.*;

public class EvenOddSortStrategy implements SortStrategy {
    private final String fieldName;

    public EvenOddSortStrategy() {
        this("power");
    }

    public EvenOddSortStrategy(String fieldName) {
        this.fieldName = fieldName;
    }


    @Override
    public void sort(Sortable[] items) {
        if (items == null || items.length <= 1) {
            return;
        }

        // Собираем четные значения и их индексы
        int[] evenValues = new int[items.length];
        int[] evenIndices = new int[items.length];
        int evenCount = 0;

        for (int i = 0; i < items.length; i++) {
            if (items[i] == null || !items[i].isValid()) {
                continue;
            }

            int value = items[i].getNumericField(fieldName);
            if (value % 2 == 0) {
                evenValues[evenCount] = value;
                evenIndices[evenCount] = i;
                evenCount++;
            }
        }

        // Сортируем четные значения (пузырьком)
        for (int i = 0; i < evenCount - 1; i++) {
            for (int j = 0; j < evenCount - i - 1; j++) {
                if (evenValues[j] > evenValues[j + 1]) {
                    int temp = evenValues[j];
                    evenValues[j] = evenValues[j + 1];
                    evenValues[j + 1] = temp;
                }
            }
        }

        // Создаем новые объекты с отсортированными четными значениями
        for (int i = 0; i < evenCount; i++) {
            int index = evenIndices[i];
            int newValue = evenValues[i];
            items[index] = createCopyWithField(items[index], fieldName, newValue);
        }
    }

    private Sortable createCopyWithField(Sortable original, String fieldName, int newValue) {
        if (original instanceof Car car) {
            Car copy = (Car) car.copy();  // создаем копию

            // Возвращаем копию (метод copy() уже создал объект)
            // Но для изменения поля все равно нужен Builder
            if ("power".equals(fieldName)) {
                return new Car.Builder()
                        .power(newValue)
                        .model(copy.getModel())
                        .year(copy.getYear())
                        .build();
            } else if ("year".equals(fieldName)) {
                return new Car.Builder()
                        .power(copy.getPower())
                        .model(copy.getModel())
                        .year(newValue)
                        .build();
            }
        }
        throw new UnsupportedOperationException();
    }

    private void swap(Sortable[] items, int i, int j) {
        Sortable temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    @Override
    public String getName() {
        return "Сортировка четных значений по мощности";
    }
}