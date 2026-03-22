
package org.example.File;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonToken;
import org.example.Collection.SortableCollection;
import org.example.Entity.Sortable;

import java.io.IOException;
import java.util.function.Supplier;

public class SortableCollectionTypeAdapter<T extends Sortable, C extends SortableCollection<T>>
        extends TypeAdapter<C> {

    private final TypeAdapter<T> elementAdapter;
    private final Supplier<C> collectionFactory;

    public SortableCollectionTypeAdapter(TypeAdapter<T> elementAdapter,
                                         Supplier<C> collectionFactory) {
        this.elementAdapter = elementAdapter;
        this.collectionFactory = collectionFactory;
    }

    @Override
    public void write(JsonWriter out, C collection) {
        try {
            if (collection == null) {
                out.nullValue();
                return;
            }

            out.beginArray();
            for (T item : collection) {
                if (item == null) {
                    out.nullValue();
                } else {
                    elementAdapter.write(out, item);
                }
            }
            out.endArray();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка сериализации", e);
        }
    }

    @Override
    public C read(JsonReader in) {
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            C collection = collectionFactory.get();

            in.beginArray();
            while (in.hasNext()) {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    continue;
                }

                T item = elementAdapter.read(in);
                if (item != null) {
                    collection.add(item);
                }
            }
            in.endArray();

            return collection;

        } catch (IOException e) {
            throw new RuntimeException("Ошибка десериализации", e);
        }
    }
}
