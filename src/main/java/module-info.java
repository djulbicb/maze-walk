module com.example.mazewalk {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mazewalk to javafx.fxml;
    exports com.example.mazewalk;
}