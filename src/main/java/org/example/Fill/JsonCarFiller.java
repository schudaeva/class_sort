package org.example.Fill;


import org.example.Collection.SortableArrayList;
import org.example.Entity.Car;
import org.example.Entity.Sortable;
import org.example.File.JsonManager;
import org.example.util.CarFactory;
import org.example.util.SortableFactory;

public class JsonCarFiller implements DataFiller {
    private final String filename;
    private final SortableFactory factory;

    // Конструктор по умолчанию использует CarFactory
    public JsonCarFiller(String filename) {
        this(filename, new CarFactory());
    }

    // Основной конструктор с возможностью указать свою фабрику
    public JsonCarFiller(String filename, SortableFactory factory) {
        this.filename = filename;
        this.factory = factory;
    }

    public SortableArrayList<Sortable> fill(int lenght){
        SortableArrayList<Sortable> collection = new SortableArrayList<>();
        JsonManager json = new JsonManager(Car.class, SortableArrayList::new, filename);
        json.load().stream()
                .limit(lenght)
                .forEach(collection::add);

        return collection;
    }

}
