package sort;

import entity.Sortable;

/**
 * Интерфейс для всех стратегий сортировки объектов, которые реализуют Sortable.
 */
public interface SortStrategy {
    void sort(Sortable[] items);
    String getName();
}