package org.example.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

// Контроллер для главной менюшки
public class MenuController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /////////////////////////////////////ТАБЛИЦА С ЗАДАЧАМИ/////////////////////////////////////
    @FXML
    private void ClickWorkPlace(ActionEvent event) {
        loadScene("WorkPlace.fxml", event);
    }

    /////////////////////////////////////ТИПО ОБРАТНАЯ СВЯЗЬ/////////////////////////////////////
    @FXML
    private void ClickFeedBack() {
        try {
            URI uri = new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            System.err.println("Ошибка при открытии браузера: " + e.getMessage());
        } catch (URISyntaxException e) {
            System.err.println("Некорректный URI: " + e.getMessage());
        }
    }

    /////////////////////////////////////НАСТРОЙКИ/////////////////////////////////////
    @FXML
    private void openSettings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            Parent root = loader.load();

            // Получаем текущий Stage из источника события
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Настройки");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadScene(String fxmlFile, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Получаем текущий Stage из источника события
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Главное меню");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////ВЫХОД ИЗ ПРОГРАММЫ/////////////////////////////////////
    @FXML
    private void ExitButtonClick() {
        Platform.exit();
    }
}
