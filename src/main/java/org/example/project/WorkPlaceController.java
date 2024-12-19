package org.example.project; // Убедитесь, что это правильный пакет

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.Optional;

public class WorkPlaceController {
    @FXML
    private Button addbutton;

    @FXML
    private VBox LabelContainer; // Убедитесь, что вы добавили этот контейнер в FXML

    @FXML
    public void initialize() {
        addbutton.setOnAction(e -> showInputDialog());
    }
    @FXML
    private void showInputDialog (){
        TextInputDialog vvodKategorii=new TextInputDialog();
        //Диалоговое окно для ввода стандарт
        vvodKategorii.setTitle("Ввод категории задачи ");
        vvodKategorii.setHeaderText("Введите назване категории задачи:");
        vvodKategorii.setContentText("Вводите");
        Optional<String> result = vvodKategorii.showAndWait();
        //Этот метод возвращает объект Optional<String>, который может содержать введенный текст,
        // если пользователь нажал "OK", или быть пустым, если пользователь закрыл диалог без ввода текста.
        result.ifPresent(this::addLabel);
        //Этот код использует метод ifPresent() объекта Optional. Если пользователь ввел текст и нажал "OK", result будет содержать значение (строку), и метод addLabel будет вызван с этим значением.
        //Если пользователь закрыл диалог без ввода текста, result будет пустым, и метод addLabel не будет вызван.
    }

    private void addLabel(String text) {
        Label newLabel = new Label(text);
        LabelContainer.getChildren().add(newLabel);
    }
}
