package sort;

import entity.Car;
import entity.Sortable;

/**
 * Сортировка автомобилей по модели (строка)
 */
public class SortByModelStrategy implements SortStrategy {

    @Override
    public void sort(Sortable[] items) {
        // Пузырьковая сортировка по строке
        for (int i = 0; i < items.length - 1; i++) {
            for (int j = 0; j < items.length - i - 1; j++) {
                Car a = (Car) items[j];
                Car b = (Car) items[j + 1];
                if (a.getModel().compareTo(b.getModel()) > 0) {
                    items[j] = b;
                    items[j + 1] = a;
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Sort by Model";
    }
}