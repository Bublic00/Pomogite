package org.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private VBox LabelContainer; // Контейнер для меток с задачами
    @FXML
    private VBox timeContainer; // Контейнер для меток с временем

    private Map<LocalDate, List<String>> plansMap = new HashMap<>();

    @FXML
    public void initialize() {
        addbutton.setOnAction(e -> showInputDialog());
        savePlansButton.setOnAction(e -> savePlans());
        datePicker.setOnAction(e -> displayPlansForSelectedDate());

        // Заполнение времени в контейнере
        populateTimeContainer();
    }

    private void populateTimeContainer() {
        for (int hour = 6; hour <= 22; hour++) {
            Label timeLabel = new Label(String.format("%02d:00", hour));
            timeContainer.getChildren().add(timeLabel);
        }
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
            displayPlansForSelectedDate();
        } else {
            System.out.println("Выберите дату и введите планы");
        }
    }

    private void displayPlansForSelectedDate() {
        LocalDate selectedDate = datePicker.getValue();
        LabelContainer.getChildren().clear(); // Очистка перед другой датой
        if (selectedDate != null && plansMap.containsKey(selectedDate)) {
            List<String> plans = plansMap.get(selectedDate);
            for (String plan : plans) {
                addLabel("Планы на " + selectedDate + ": " + plan);
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
