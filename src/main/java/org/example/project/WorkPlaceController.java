package org.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
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
    private VBox plansContainer; // Контейнер для отображения планов
    @FXML
    private VBox categoriesContainer; // Контейнер для отображения категорий
    @FXML
    private TextField categoryNameField; // Поле для ввода названия категории
    @FXML
    private ColorPicker categoryColorPicker; // ColorPicker для выбора цвета категории

    @FXML
    private  Label DataLabel;
    @FXML
    private ComboBox<Category> categoryComboBox; // Комбо-бокс для выбора категории

    private List<Category> categories; // Список категорий
    private Map<LocalDate, List<Plan>> plans; // Хранение планов по датам

    @FXML
    public void initialize() {
        categoryComboBox.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return category != null? category.getName():"";
            }

            @Override
            public Category fromString(String s) {
                return null;
            }
        });
        setText();
        categories = new ArrayList<>();
        plans = new HashMap<>();
        addPlanButton.setOnAction(e -> handleAddPlan());
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
                Plan newPlan = new Plan(time, planText, selectedCategory); // Передаем категорию в конструктор
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
        plansContainer.getChildren().clear();
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            List<Plan> plansForDate = plans.getOrDefault(selectedDate, new ArrayList<>());
            plansForDate.sort(Comparator.comparing(Plan::getTime));
            for (Plan plan : plansForDate) {
                HBox planCard = createPlanCard(plan);
                plansContainer.getChildren().add(planCard);
            }
        }
    }

    private HBox createPlanCard(Plan plan) {
        HBox planCard = new HBox(10);
        planCard.setStyle("-fx-padding: 10; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        Circle colorCircle = new Circle(5);
        colorCircle.setFill(plan.getCategory().getColor());
        Label planLabel = new Label(plan.toString());
        planLabel.setWrapText(true);

        Button deleteButton = new Button("Удалить");
        deleteButton.setOnAction(e -> {
            plans.get(datePicker.getValue()).remove(plan);
            updatePlansDisplay();
        });

        planCard.getChildren().addAll(colorCircle,planLabel, deleteButton);
        return planCard;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setText()
    {
        LocalDate localDate = LocalDate.now();
        int todayData = localDate.getDayOfMonth();
        String todayMonth = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        DataLabel.setText(todayData + " " + todayMonth);
    }
}
