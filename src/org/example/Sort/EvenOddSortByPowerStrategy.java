package org.example.Sort;
import org.example.Entity.Sortable;

public class EvenOddSortByPowerStrategy implements SortStrategy {

    @Override
    public void sort(Sortable[] items) {
        for (int i = 0; i < items.length - 1; i++) {
            for (int j = 0; j < items.length - i - 1; j++) {

                int a = (int) items[j].getFieldValue("power");
                int b = (int) items[j + 1].getFieldValue("power");

                // сортируем только если ОБА четные
                if (a % 2 == 0 && b % 2 == 0) {
                    if (a > b) {
                        swap(items, j, j + 1);
                    }
                }
            }
        }
    }

    private void swap(Sortable[] items, int i, int j) {
        Sortable temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    @Override
    public String getName() {
        return "Even/Odd Sort by Power";
    }
}