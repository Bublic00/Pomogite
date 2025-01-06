package org.example.project;

import java.time.LocalTime;

public class Plan {
    private LocalTime time;
    private String text;
    private Category category;


    public Plan(LocalTime time, String text, Category selectedCategory) {
        this.time = time;
        this.text = text;
        this.category=selectedCategory;
    }

    public LocalTime getTime() {
        return time;
    }

    public Category getCategory() {
        return category;
    }

    public String getText() {
        return text;
    }

    public String getCategoryName(){
return  (category!= null)? category.getName():"Без категории";
    }

    @Override
    public String toString(){
        return String.format("Время: %s - План: %s - Категория: %s", time, text, category.getName());
    }
}
