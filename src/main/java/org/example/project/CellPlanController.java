package org.example.project;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.LocalTime;

public class CellPlanController extends AnchorPane {

    public CellPlanController(String text, LocalTime time, Color color) {
        setPrefHeight(150); // Задайте подходящую высоту
        setPrefWidth(155);
        setMinWidth(155);   // Минимальная ширина
        setMinHeight(150);   // Минимальная высота
        setMaxWidth(155);    // Максимальная ширина
        setMaxHeight(150);    // Максимальная высота
        initialize(text, time, color);
    }

    private void initialize(String text, LocalTime time, Color color) {
        AnchorPane anchorPane = new AnchorPane();

        TextFlow textFlow = new TextFlow();
        Text label = new Text(text);
        textFlow.getChildren().add(label);
        textFlow.setPrefWidth(180); // Установите предпочтительную ширину для TextFlow
        textFlow.setPrefWidth(130); // Установите ширину для автоматического переноса текста
        setTopAnchor(textFlow, 1.0);
        setLeftAnchor(textFlow, 10.0);


        Label label2 = new Label();
        label2.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        setTopAnchor(label2, 0.0);
        setLeftAnchor(label2, -2.0);
        label2.setPrefHeight(150);
        label2.setPrefWidth(10);

        Label label3 = new Label(time.toString());
        setTopAnchor(label3, 135.0);
        setLeftAnchor(label3, 10.0);
        label3.setPrefHeight(15);
        label3.setPrefWidth(180);
        // Добавление элементов в AnchorPane
        this.getChildren().addAll(textFlow, label2, label3);
    }
}