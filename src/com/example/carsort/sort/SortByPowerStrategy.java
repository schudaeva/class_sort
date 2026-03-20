package sort;

import entity.Car;
import entity.Sortable;

/**
 * Сортировка автомобилей по мощности (число)
 */
public class SortByPowerStrategy implements SortStrategy {

    @Override
    public void sort(Sortable[] items) {
        // Пузырьковая сортировка
        for (int i = 0; i < items.length - 1; i++) {
            for (int j = 0; j < items.length - i - 1; j++) {
                Car a = (Car) items[j];
                Car b = (Car) items[j + 1];
                if (a.getPower() > b.getPower()) {
                    items[j] = b;
                    items[j + 1] = a;
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Sort by Power";
    }
}