package org.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.*;

public class WorkPlaceController {
    @FXML
    private Button addbutton;
    @FXML
    private Button savePlansButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea plansTextArea;

    @FXML
    private VBox LabelContainer; // Контейнер для меток с планами

    private Map<LocalDate, List<String>> plansMap = new HashMap<>();

    @FXML
    public void initialize() {
        addbutton.setOnAction(e -> showInputDialog());
        savePlansButton.setOnAction(e -> savePlans());
        datePicker.setOnAction(e -> displayPlansForSelectedDate());
    }

    @FXML
    private void showInputDialog() {
        TextInputDialog vvodKategorii = new TextInputDialog();
        vvodKategorii.setTitle("Ввод категории задачи");
        vvodKategorii.setHeaderText("Введите название категории задачи:");
        vvodKategorii.setContentText("Вводите");
        Optional<String> result = vvodKategorii.showAndWait();
        result.ifPresent(this::addLabel);
    }

    private void addLabel(String text) {
        Label newLabel = new Label(text);
        LabelContainer.getChildren().add(newLabel);
    }

    private void savePlans() {
        LocalDate selectedDate = datePicker.getValue();
        String plans = plansTextArea.getText();
        if (selectedDate != null && !plans.isEmpty()) {
            plansMap.computeIfAbsent(selectedDate, k -> new ArrayList<>()).add(plans);
            plansTextArea.clear();
            displayPlansForSelectedDate(); // Обновляем отображение планов для выбранной даты
        } else {
            System.out.println("Выберите дату и введите планы");
        }
    }

    private void displayPlansForSelectedDate() {
        LocalDate selectedDate = datePicker.getValue();
        LabelContainer.getChildren().clear(); // Очищаем только контейнер меток
        if (selectedDate != null && plansMap.containsKey(selectedDate)) {
            List<String> plans = plansMap.get(selectedDate);
            for (String plan : plans) {
                addLabel("Планы на " + selectedDate + ": " + plan);
            }
        }
    }
}
