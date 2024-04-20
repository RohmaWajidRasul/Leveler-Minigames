module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens menu to javafx.fxml;
    exports menu;
}