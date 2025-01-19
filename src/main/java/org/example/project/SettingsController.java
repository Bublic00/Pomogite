package org.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    private ComboBox<String> themeComboBox;
    @FXML
    private Button saveButton;
    private MenuController menuController;

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    @FXML
    public void initialize() {
        // Заполнение ComboBox доступными темами
        themeComboBox.getItems().addAll("Светлая", "Темная");
    }

    @FXML
    public void saveSettings() {
        String selectedTheme = themeComboBox.getValue();
        if (selectedTheme != null) {
            applyTheme(selectedTheme);
            showAlert("Настройка установлена", "Выбрана тема: " + selectedTheme);
        } else {
            showAlert("Ошибка", "Пожалуйста, выберите тему.");
        }
    }

    private void applyTheme(String theme) {
        Stage stage = (Stage) saveButton.getScene().getWindow(); // Получаем текущее окно
        String cssFile = theme.equals("Светлая") ? "light-theme.css" : "dark-theme.css"; // Определяем файл стилей
        stage.getScene().getStylesheets().clear(); // Очищаем текущие стили
        stage.getScene().getStylesheets().add(getClass().getResource(cssFile).toExternalForm()); // Добавляем новый стиль
    }

    @FXML
    private void Back_onMenu(ActionEvent event) {
        if (menuController != null) {
            menuController.loadScene("hello-view.fxml",event);
        } else {
            System.out.println("MenuController is null");
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
