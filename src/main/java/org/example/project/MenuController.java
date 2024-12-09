package org.example.project;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    public  void ExitButtonClick(){
        Platform.exit();
    }

}