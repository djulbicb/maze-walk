module com.example.mazewalk {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mazewalk to javafx.fxml;
    exports com.example.mazewalk;
    exports com.example.mazewalk.old;
    opens com.example.mazewalk.old to javafx.fxml;
}