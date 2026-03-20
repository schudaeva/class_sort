package sort;

import entity.Car;
import entity.Sortable;
import java.util.ArrayList;
import java.util.List;

/**
 * Доп. задание 1:
 * Сортирует только автомобили с четной мощностью.
 * Нечетные мощности остаются на своих местах.
 */
public class EvenOddSortByPowerStrategy implements SortStrategy {

    @Override
    public void sort(Sortable[] items) {
        List<Car> evenCars = new ArrayList<>();

        // Собираем только четные мощности
        for (Sortable item : items) {
            Car car = (Car) item;
            if (car.getPower() % 2 == 0) {
                evenCars.add(car);
            }
        }

        // Сортируем четные мощности (Bubble sort)
        for (int i = 0; i < evenCars.size() - 1; i++) {
            for (int j = 0; j < evenCars.size() - i - 1; j++) {
                if (evenCars.get(j).getPower() > evenCars.get(j + 1).getPower()) {
                    Car temp = evenCars.get(j);
                    evenCars.set(j, evenCars.get(j + 1));
                    evenCars.set(j + 1, temp);
                }
            }
        }

        // Подставляем обратно на места четных
        int index = 0;
        for (int i = 0; i < items.length; i++) {
            Car car = (Car) items[i];
            if (car.getPower() % 2 == 0) {
                items[i] = evenCars.get(index++);
            }
        }
    }

    @Override
    public String getName() {
        return "Even/Odd Sort by Power";
    }
}