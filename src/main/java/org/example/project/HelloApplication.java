package org.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Размеры и расположение окна (панель задач?)
        stage.initStyle(StageStyle.UNDECORATED);
        Screen screen = Screen.getPrimary();
        stage.setWidth(screen.getVisualBounds().getWidth());
        stage.setHeight(screen.getVisualBounds().getHeight());





        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene mainMenu = new Scene(fxmlLoader.load());
        MenuController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.setTitle("Vacuole");
        stage.setScene(mainMenu);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

