package org.example.Entity;

public interface Sortable {
    int getNumericField(String fieldName);
    String getStringField(String fieldName);
    String toFileString();

    default boolean isValid() {
        return true;
    }
    // Возвращает список доступных числовых полей
    default String[] getAvailableNumericFields() {
        return new String[]{};
    }
    // Возвращает список доступных строковых полей
    default String[] getAvailableStringFields() {
        return new String[]{};
    }
}
