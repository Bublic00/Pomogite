package org.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javax.sound.sampled.DataLine;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.*;

public class WorkPlaceController {

    // Элементы интерфейса, которые будут связаны с FXML
    @FXML
    private DatePicker datePicker; // Элемент для выбора даты

    @FXML
    private TextField timeTextField; // Поле для ввода времени

    @FXML
    private TextArea plansTextArea; // Область для ввода текста плана

    @FXML
    private Button addPlanButton; // Кнопка для добавления плана

    @FXML
    private VBox plansContainer; // Контейнер для отображения планов

    @FXML
    private  Label DataLabel;

    // Хранение планов в виде словаря, где ключ - дата, значение - список планов
    private Map<LocalDate, List<Plan>> plans;


    @FXML
    public void initialize() {
        plans = new HashMap<>(); // Инициализация словаря для хранения планов
        setText();
        addPlanButton.setOnAction(e -> handleAddPlan());
    }

    // Метод для обработки добавления нового плана
    @FXML
    private void handleAddPlan() {
        LocalDate selectedDate = datePicker.getValue();
        String enteredTime = timeTextField.getText().trim();
        String planText = plansTextArea.getText().trim();

        if (selectedDate != null && !enteredTime.isEmpty() && !planText.isEmpty()) {
            try {
                LocalTime time = LocalTime.parse(enteredTime); // Проверка формата времени
                Plan newPlan = new Plan(time, planText);

                // Добавляем новый план в список для выбранной даты
                plans.computeIfAbsent(selectedDate, k -> new ArrayList<>()).add(newPlan);

                updatePlansDisplay(); // Обновляем отображение планов
                plansTextArea.clear();
                timeTextField.clear();
            } catch (DateTimeParseException e) {
                showAlert("Ошибка", "Неверный формат времени. Используйте HH:mm.");
            }
        } else {
            showAlert("Ошибка", "Пожалуйста, выберите дату, введите время и план.");
        }
    }

    // Метод для обновления отображения планов в интерфейсе
    private void updatePlansDisplay() {
        plansContainer.getChildren().clear(); // Очищаем контейнер перед обновлением

        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            List<Plan> plansForDate = plans.getOrDefault(selectedDate, new ArrayList<>());

            // Сортировка планов по времени
            plansForDate.sort(Comparator.comparing(Plan::getTime));

            for (Plan plan : plansForDate) {
                HBox planCard = createPlanCard(plan);
                plansContainer.getChildren().add(planCard); // Добавляем карточку в контейнер
            }
        }
    }

    // Метод для создания карточки плана
    private HBox createPlanCard(Plan plan) {
        HBox planCard = new HBox(10);
        planCard.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label planLabel = new Label(plan.toString());
        planLabel.setWrapText(true);

        Button deleteButton = new Button("Удалить");
        deleteButton.setOnAction(e -> {
            plans.get(datePicker.getValue()).remove(plan);
            updatePlansDisplay(); // Обновляем отображение после удаления
        });

        planCard.getChildren().addAll(planLabel, deleteButton);
        return planCard;
    }

    // Метод для отображения сообщения об ошибке
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Создаем новое окно сообщения
        alert.setTitle(title); // Устанавливаем заголовок
        alert.setHeaderText(null); // Убираем заголовок
        alert.setContentText(message); // Устанавливаем текст сообщения
        alert.showAndWait(); // Показываем окно и ждем, пока оно будет закрыто
    }

    private void setText()
    {
        LocalDate localDate = LocalDate.now();
        int todayData = localDate.getDayOfMonth();
        String todayMonth = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        DataLabel.setText(todayData + " " + todayMonth);
    }
}
