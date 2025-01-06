package org.example.project;

import javafx.scene.paint.Color;

import java.time.LocalTime;

public class Plan {
    private LocalTime time;
    private String text;
    private Color color  ;

    public Plan(LocalTime time, String text) {
        this.time = time;
        this.text = text;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getText() {
        return text;
    }
    public  void setColor(Color color){
        this.color = color;
    }
    public Color getColor(){
        return color ;
    }
    @Override
    public String toString(){
        return String.format("Время: %s - План: %s",time,text);
    }
}
