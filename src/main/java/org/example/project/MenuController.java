package org.example.project;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

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
