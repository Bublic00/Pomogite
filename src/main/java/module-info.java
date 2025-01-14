module org.example.project {
    requires javafx.controls;
    requires javafx.fxml;


    requires java.desktop;
    requires java.sql;

    opens org.example.project to javafx.fxml;
    exports org.example.project;
}