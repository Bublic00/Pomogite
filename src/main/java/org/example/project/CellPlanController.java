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
        setPrefHeight(80); // Задайте подходящую высоту
        setPrefWidth(175);
        setMinWidth(175);   // Минимальная ширина
        setMinHeight(80);   // Минимальная высота
        setMaxWidth(175);    // Максимальная ширина
        setMaxHeight(80);    // Максимальная высота
        initialize(text, time, color);
    }

    private void initialize(String text, LocalTime time, Color color) {

        Label label2 = new Label();
        label2.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        setTopAnchor(label2, 0.0);
        setLeftAnchor(label2, 0.0);
        label2.setPrefHeight(80);
        label2.setPrefWidth(15);
        label2.setStyle("-fx-background-radius: 15 0 0 15; -fx-background-color: " + colorToHex(color) + ";");
        label2.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

        TextFlow textFlow = new TextFlow();
        Text label = new Text(text);
        textFlow.getChildren().add(label);
        textFlow.setPrefWidth(175); // Установите предпочтительную ширину для TextFlow
        textFlow.setPrefWidth(65); // Установите ширину для автоматического переноса текста
        setTopAnchor(textFlow, 1.0);
        setLeftAnchor(textFlow, 22.0);

        Label label3 = new Label(time.toString());
        setTopAnchor(label3, 60.0);
        setLeftAnchor(label3, 22.0);
        label3.setPrefHeight(15);
        label3.setPrefWidth(180);
        // Добавление элементов в AnchorPane
        setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #000000; -fx-border-width: 1;");
        this.getChildren().addAll(label2, label3, textFlow);
    }

    public String colorToHex(Color color) {
        String hex = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        return hex;
    }
}