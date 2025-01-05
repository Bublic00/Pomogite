package org.example.project;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.awt.Desktop; // Исправленный импорт
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MenuController {

    @FXML
    private Button button;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void ClickWorkPlace() {
        loadScene("WorkPlace.fxml");
    }

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

    @FXML
    private void openSettings() {
        loadScene("Settings.fxml");
    }

    public void loadScene(String fxmlFile) {
        if (stage != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = fxmlLoader.load();
                Scene newScene = new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                stage.setScene(newScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Stage is null");
        }
    }

    @FXML
    private void ExitButtonClick() {
        Platform.exit();
    }
}
