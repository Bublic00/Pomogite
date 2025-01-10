package org.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import javafx.event.ActionEvent; // Импортируем ActionEvent
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.*;
import javafx.scene.Node; // Импортируем Node

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
    private ListView<Plan> mondayListView; // ListView для понедельника
    @FXML
    private ListView<Plan> tuesdayListView; // ListView для вторника
    @FXML
    private ListView<Plan> wednesdayListView; // ListView для среды
    @FXML
    private ListView<Plan> thursdayListView; // ListView для четверга
    @FXML
    private ListView<Plan> fridayListView; // ListView для пятницы
    @FXML
    private ListView<Plan> saturdayListView; // ListView для субботы
    @FXML
    private ListView<Plan> sundayListView; // ListView для воскресенья
    @FXML
    private VBox categoriesContainer; // Контейнер для отображения категорий
    @FXML
    private TextField categoryNameField; // Поле для ввода названия категории
    @FXML
    private ColorPicker categoryColorPicker; // ColorPicker для выбора цвета категории
    @FXML
    private ComboBox<Category> categoryComboBox; // Комбо-бокс для выбора категории
    @FXML
    private Label dataLabel;
    @FXML
    private HBox Hbox;
    @FXML
    private Label DayOfWeekMonday;
    private List<Category> categories; // Список категорий
    private Map<LocalDate, List<Plan>> plans; // Хранение планов по датам
    @FXML
    private void Back_onMenu(ActionEvent event){

        try{
            FXMLLoader fxloader=new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root=fxloader.load();
            Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();

            Screen screen = Screen.getPrimary();
            stage.setWidth(screen.getVisualBounds().getWidth());
            stage.setHeight(screen.getVisualBounds().getHeight());

            stage.setScene(new Scene(root));
                stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

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
                        case 0: mondayListView.getItems().add(plan); break; // Пн
                        case 1: tuesdayListView.getItems().add(plan); break; // Вт
                        case 2: wednesdayListView.getItems().add(plan); break; // Ср
                        case 3: thursdayListView.getItems().add(plan); break; // Чт
                        case 4: fridayListView.getItems().add(plan); break; // Пт
                        case 5: saturdayListView.getItems().add(plan); break; // Сб
                        case 6: sundayListView.getItems().add(plan); break; // Вс
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