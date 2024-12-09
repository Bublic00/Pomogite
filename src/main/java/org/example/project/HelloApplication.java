package org.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader_Mainmenu = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader_WorkPlace = new FXMLLoader(HelloApplication.class.getResource("WorkPlace.fxml"));

        Scene MainMenu = new Scene(fxmlLoader_Mainmenu.load(),Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);
        //Тута определяется окно рабочее)
        Scene WorkPlace =new Scene(fxmlLoader_WorkPlace.load(),Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE);

        stage.setTitle("Hello!");
        stage.setScene(MainMenu);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}