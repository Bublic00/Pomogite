package org.example.project;

import javafx.geometry.Insets;
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
        setMaxWidth(175);
        setMinWidth(175);
        initialize(text, time, color);
    }

    private void initialize(String text, LocalTime time, Color color) {
        TextFlow textFlow = new TextFlow();
        Text label = new Text(text);
        label.setWrappingWidth(140);
        textFlow.getChildren().add(label);

        setTopAnchor(textFlow, 1.0);
        setLeftAnchor(textFlow, 20.0);
        setRightAnchor(textFlow, 20.0);

        Label label2 = new Label();
        label2.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        setTopAnchor(label2, 0.0);
        setLeftAnchor(label2, 0.0);
        label2.setPrefWidth(15);
        label2.setStyle("-fx-background-radius: 15 0 0 15; -fx-background-color: " + colorToHex(color) + ";");

        // Создание метки для времени
        Label label3 = new Label(time.toString());
        setBottomAnchor(label3, 5.0); // Устанавливаем отступ от нижней границы
        setLeftAnchor(label3, 20.0);
        label3.setPrefHeight(15);
        label3.setPrefWidth(175);

        // Установка стиля
        setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #000000; -fx-border-width: 1;");

        // Добавление элементов в AnchorPane
        this.getChildren().addAll(label2, textFlow, label3);

        // Обновление высоты AnchorPane в зависимости от высоты текста
        textFlow.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            double newHeightValue = newHeight.doubleValue() + 50; // Учитываем дополнительные отступы
            this.setPrefHeight(newHeightValue);
        });

        // Установка начальной высоты AnchorPane

        this.setPrefHeight(calculateTextHeight(text, 140) + 50); // Устанавливаем начальную высоту
        label2.setPrefHeight(this.getPrefHeight());

    }

    private double calculateTextHeight(String text, double wrappingWidth) {
        Text textNode = new Text(text);
        textNode.setWrappingWidth(wrappingWidth);
        return textNode.getLayoutBounds().getHeight();
    }

    public String colorToHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
