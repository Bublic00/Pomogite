package org.example.project;

import javafx.scene.paint.Color;

public class Category {
    private String name;
    private Color color;

    public Category(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
    public String toString(){
        return name;
    }
}
