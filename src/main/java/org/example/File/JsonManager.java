package org.example.File;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.example.Collection.SortableCollection;
import org.example.Entity.Sortable;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.Supplier;

public class JsonManager {
    private final Gson gson;
    private final Type type;
    private String path;

    public <T extends Sortable, C extends SortableCollection<T>> JsonManager(Class<T> classEntity,
                                                                             Supplier<C> creatingCollection) {
        Gson gsonBase = new Gson();

        TypeAdapter<T> elementAdapter = gsonBase.getAdapter(classEntity);

        this.gson = new GsonBuilder().registerTypeHierarchyAdapter(
                SortableCollection.class, new SortableCollectionTypeAdapter<>(
                        elementAdapter, creatingCollection)
        ).create();

        this.type = new TypeToken<SortableCollection<Sortable>[]>(){}.getType();

    }

    public <T extends Sortable, C extends SortableCollection<T>> JsonManager(Class<T> classEntity,
                                                                             Supplier<C> creatingCollection,
                                                                             String path) {
        this(classEntity, creatingCollection);
        this.path = path;
    }


    public void clear() {
        try (FileWriter writer = new FileWriter(this.path)) {
            writer.write("[]");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public <T extends Sortable> void save(SortableCollection<T> object) {
        clear();
        append(object);
    }

    public void setPath(String path) {
        this.path = path;
    }


    public <T extends Sortable> void append(SortableCollection<T> collection) {

        File file = new File(this.path);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SortableCollection<T>[] existing = null;

        try (Reader reader = new FileReader(file)) {
            existing = gson.fromJson(reader, type);
        } catch (IOException ignored) {}

        int oldLength = existing != null ? existing.length : 0;

        SortableCollection<T>[] base =
                existing != null ? existing : (SortableCollection<T>[]) new SortableCollection[0];

        SortableCollection<T>[] result = Arrays.copyOf(base, oldLength + 1);
        result[oldLength] = collection;

        write(result);
    }

    public void appendValues(Sortable entity, int indexCollection){
        SortableCollection<Sortable>[] array = loadArray();
        SortableCollection<Sortable> collection = array[indexCollection];
        collection.add(entity);
        array[indexCollection] = collection;
        write(array);
    }

    public <T extends Sortable> SortableCollection<T>[] loadArray() {
        try (FileReader reader = new FileReader(this.path)) {
            return gson.fromJson(reader, this.type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public <T extends Sortable> SortableCollection<T> load() {
        try {
            return (SortableCollection<T>) loadArray()[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Не удалось загрузить коллекцию" + e);
            return null;
        }

    }

    private <T extends Sortable> void write(SortableCollection<T>[] collections) {

        File file = new File(this.path);


        try (Writer writer = new FileWriter(file)) {
            gson.toJson(collections, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



/*  здесь работа с записью лишь одной коллекции. Не массива коллекций, а одной коллекции. Перед продом надо удалить.
    public <T extends Sortable> void saveCollection(SortableCollection<T> object) {

        try (FileWriter writer = new FileWriter(this.path)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends Sortable> SortableCollection<T> loadCollection() {
        try (FileReader reader = new FileReader(this.path)) {
            return gson.fromJson(reader, new TypeToken<SortableCollection<Sortable>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

 */

}

