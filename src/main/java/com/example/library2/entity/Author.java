package com.example.library2.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Author implements Serializable {
    private final StringProperty name = new SimpleStringProperty("Author name");
    private final IntegerProperty id = new SimpleIntegerProperty(99999);
    private final StringProperty description = new SimpleStringProperty("Description author");

    public Author() {
    }

    public Author(String name, int id, String description) {
        this.name.set(name);
        this.id.set(id);
        this.description.set(description);
    }

    @Override
    public String toString() {
        return "Author{" +
                "name=" + name +
                ", id=" + id +
                ", description=" + description +
                '}';
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }
}
