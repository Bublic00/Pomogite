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
        // Загружаем FXML файл
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene mainMenu = new Scene(fxmlLoader.load(), Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        // Получаем доступ к контроллеру
        MenuController controller = fxmlLoader.getController();
        controller.setStage(stage); // Устанавливаем сцену в контроллере

        // Настраиваем и отображаем сцену
        stage.setTitle("Hello!");
        stage.setScene(mainMenu);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

