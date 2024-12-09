package org.example.project;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        if (stage != null) {
            try {
                FXMLLoader fxmlLoader_WorkPlace = new FXMLLoader(getClass().getResource("WorkPlace.fxml"));
                Scene workPlaceScene = new Scene(fxmlLoader_WorkPlace.load(), Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                stage.setScene(workPlaceScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Stage is null");
        }
    }
    @FXML
    private void ExitButtonClick(){
        Platform.exit();
    }
}
