package org.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class SettingsController {
    @FXML
    private ComboBox<String> themeComboBox;
    @FXML
    private Button saveButton;
    private MenuController menuController;

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }
    @FXML
    public void saveSettings(){
        String select_Theme=themeComboBox.getValue();
        showAlert("Настройка установлена","выбрана тема"+select_Theme);

    }
    @FXML
    private void Back_onMenu(){
        if (menuController!=null){
            menuController.loadScene("hell0-view");
        } else {
            System.out.println("MenuController is null");
        }
    }
    private void showAlert(String title,String message){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
}
