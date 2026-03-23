package org.example.File;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.example.Collection.SortableCollection;
import org.example.Entity.Sortable;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.function.Supplier;

public class JsonManager {
    private final Gson gson;
    private final Type type;

    public <T extends Sortable, C extends SortableCollection<T>> JsonManager(Class<T> classEntity,
                                                                             Supplier<C> creatingCollection) {
        Gson gsonBase = new Gson();


        this.type = TypeToken.getParameterized(creatingCollection.get().getClass(), classEntity).getType();


        TypeAdapter<T> elementAdapter = gsonBase.getAdapter(classEntity);

        this.gson = new GsonBuilder().registerTypeHierarchyAdapter(
                SortableCollection.class, new SortableCollectionTypeAdapter<>(
                        elementAdapter, creatingCollection)
        )
        .create();
    }



    public <T> void save(String path, T object) {
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public <T> T load(String path) {
        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, this.type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


//    использование
//
//    JsonSaver saver = new JsonSaver<>(Car.class, SortableArrayList::new) {};
//
//    SortableArrayList<Car> cars = saver.load("file.json");
//
//    saver.save("file.json", cars);




}

