package sort;

import entity.Car;
import entity.Sortable;

/**
 * Сортировка автомобилей по году выпуска (число)
 */
public class SortByYearStrategy implements SortStrategy {

    @Override
    public void sort(Sortable[] items) {
        // Пузырьковая сортировка
        for (int i = 0; i < items.length - 1; i++) {
            for (int j = 0; j < items.length - i - 1; j++) {
                Car a = (Car) items[j];
                Car b = (Car) items[j + 1];
                if (a.getYear() > b.getYear()) {
                    items[j] = b;
                    items[j + 1] = a;
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Sort by Year";
    }
}
