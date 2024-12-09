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
        Scene mainMenu = new Scene(fxmlLoader_Mainmenu.load(), Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

       //Когда вы загружаете FXML файл с помощью FXMLLoader, он создает экземпляр контроллера, если он указан в FXML файле с помощью атрибута fx:controller.
        // После загрузки вы можете получить доступ к этому экземпляру контроллера, вызвав метод getController().
       MenuController controller = fxmlLoader_Mainmenu.getController();


        controller.setStage(stage);

        stage.setTitle("Hello!");
        stage.setScene(mainMenu);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
