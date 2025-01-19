package org.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.*;

public class WorkPlaceController {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeTextField;
    @FXML
    private TextArea plansTextArea;
    @FXML
    private Button addPlanButton;
    @FXML
    private ListView<CellPlanController> mondayListView;
    @FXML
    private ListView<CellPlanController> tuesdayListView;
    @FXML
    private ListView<CellPlanController> wednesdayListView;
    @FXML
    private ListView<CellPlanController> thursdayListView;
    @FXML
    private ListView<CellPlanController> fridayListView;
    @FXML
    private ListView<CellPlanController> saturdayListView;
    @FXML
    private ListView<CellPlanController> sundayListView;
    @FXML
    private VBox categoriesContainer;
    @FXML
    private TextField categoryNameField;
    @FXML
    private ColorPicker categoryColorPicker;
    @FXML
    private ComboBox<Category> categoryComboBox;
    @FXML
    private Label dataLabel;
    @FXML
    private HBox Hbox;
    private List<Category> categories;
    private Map<LocalDate, List<Plan>> plans;

    @FXML
    public void initialize() {
        setTodayData();
        categoryComboBox.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return category != null ? category.getName() : "";
            }

            @Override
            public Category fromString(String s) {
                return null;
            }
        });
        categories = new ArrayList<>();
        plans = new HashMap<>();
        addPlanButton.setOnAction(e -> handleAddPlan());

        // Обработчик для изменения даты
        datePicker.setOnAction(e -> updatePlansDisplay());
    }

    //Установка текста для сегодняшнег дня
    private void setTodayData()
    {
        //Верхушка
        LocalDate localDate = LocalDate.now();
        int todayData = localDate.getDayOfMonth();
        String todayMonth = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dataLabel.setText(todayData + " " + todayMonth);

        //Даты дней недели
        LocalDate startOfWeek = localDate.with(ChronoField.DAY_OF_WEEK, 1); // Пн
        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDate = date.format(formatter);
            Label l = (Label) Hbox.getChildren().get(i);
            l.setText(formattedDate);
        }

        //Планы на эту неделю

    }
    @FXML
    private void addCategory() {
        String name = categoryNameField.getText().trim();
        Color color = categoryColorPicker.getValue();
        if (!name.isEmpty()) {
            Category newCategory = new Category(name, color);
            categories.add(newCategory);
            categoryComboBox.getItems().add(newCategory);
            createCategoryCard(newCategory);
            categoryNameField.clear();
        } else {
            showAlert("Ошибка", "Название категории не может быть пустым.");
        }
    }

    private void createCategoryCard(Category category) {
        HBox categoryCard = new HBox(10);
        categoryCard.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        Circle colorCircle = new Circle(15);
        colorCircle.setFill(category.getColor());
        Label categoryLabel = new Label(category.getName());
        categoryCard.getChildren().addAll(colorCircle, categoryLabel);
        categoriesContainer.getChildren().add(categoryCard);
    }

    @FXML
    private void handleAddPlan() {
        LocalDate selectedDate = datePicker.getValue();
        String enteredTime = timeTextField.getText().trim();
        String planText = plansTextArea.getText().trim();
        Category selectedCategory = categoryComboBox.getValue();

        if (selectedDate != null && !enteredTime.isEmpty() && !planText.isEmpty() && selectedCategory != null) {
            try {
                LocalTime time = LocalTime.parse(enteredTime);
                Plan newPlan = new Plan(time, planText, selectedCategory);
                plans.computeIfAbsent(selectedDate, k -> new ArrayList<>()).add(newPlan);
                updatePlansDisplay();
                plansTextArea.clear();
                timeTextField.clear();
            } catch (DateTimeParseException e) {
                showAlert("Ошибка", "Неверный формат времени. Используйте HH:mm.");
            }
        } else {
            showAlert("Ошибка", "Пожалуйста, выберите дату, введите время, план и выберите категорию.");
        }
    }

    private void updatePlansDisplay() {
        // Очищаем все ListView
        mondayListView.getItems().clear();
        tuesdayListView.getItems().clear();
        wednesdayListView.getItems().clear();
        thursdayListView.getItems().clear();
        fridayListView.getItems().clear();
        saturdayListView.getItems().clear();
        sundayListView.getItems().clear();

        // Получаем текущую дату и определяем начало недели
        LocalDate currentDate = datePicker.getValue();
        if (currentDate != null) {
            LocalDate startOfWeek = currentDate.with(ChronoField.DAY_OF_WEEK, 1); // Пн
            for (int i = 0; i < 7; i++) {
                LocalDate date = startOfWeek.plusDays(i);
                List<Plan> plansForDate = plans.getOrDefault(date, new ArrayList<>());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedDate = date.format(formatter);
                Label l = (Label) Hbox.getChildren().get(i);
                l.setText(formattedDate);
                for (Plan plan : plansForDate) {
                    switch (i) {
                        case 0: mondayListView.getItems().add(new CellPlanController(plan.getText(), plan.getTime(), plan.getCategory().getColor())); break;
                        case 1: tuesdayListView.getItems().add(new CellPlanController(plan.getText(), plan.getTime(), plan.getCategory().getColor())); break;
                        case 2: wednesdayListView.getItems().add(new CellPlanController(plan.getText(), plan.getTime(), plan.getCategory().getColor())); break;
                        case 3: thursdayListView.getItems().add(new CellPlanController(plan.getText(), plan.getTime(), plan.getCategory().getColor())); break;
                        case 4: fridayListView.getItems().add(new CellPlanController(plan.getText(), plan.getTime(), plan.getCategory().getColor())); break;
                        case 5: saturdayListView.getItems().add(new CellPlanController(plan.getText(), plan.getTime(), plan.getCategory().getColor())); break;
                        case 6: sundayListView.getItems().add(new CellPlanController(plan.getText(), plan.getTime(), plan.getCategory().getColor())); break;
                    }
                }
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}