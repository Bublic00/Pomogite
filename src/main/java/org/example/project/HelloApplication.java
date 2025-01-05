package org.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Загружаем FXML файл
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene mainMenu = new Scene(fxmlLoader.load(), Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

        MenuController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.setTitle("Vacuole");
        stage.setScene(mainMenu);
        stage.show();
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
    }

    public static void main(String[] args) {
        launch();
    }
}

