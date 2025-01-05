package org.example.project;

import java.time.LocalTime;

public class Plan {
    private LocalTime time;
    private String text;

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
    @Override
    public String toString(){
        return String.format("Время: %s - План: %s",time,text);
    }
}
