package org.example.project;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CellTable {
    public BorderPane cell;

    public CellTable()
    {
        this.cell = new BorderPane();
        creatCell("gfb;d", LocalDate.now());
    }

    private void creatCell(String task, LocalDate time)
    {
        cell.setMinSize(50,50);
        Label colorLabel = new Label();
        colorLabel.setPrefWidth(10);
        colorLabel.setPrefHeight(cell.getHeight());
        colorLabel.setStyle("-fx-background-color: lightblue;");

        cell.setLeft(colorLabel);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Label dateLabel = new Label(time.format(formatter));
        cell.setBottom(dateLabel);

        Label textLabel = new Label("Здесь какой-то очень длинный текст, который должен занимать большую часть пространства, а также переноситься на новые строчки.");
        textLabel.setWrapText(true);
        cell.setCenter(textLabel);
    }
}
