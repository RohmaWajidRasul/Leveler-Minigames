module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.eclipse.paho.client.mqttv3;
    requires javafx.graphics;


    opens menu to javafx.fxml;
    opens gameover to javafx.fxml;
    exports menu;
    exports minigamesPackage;
    exports handlers;
    opens handlers to javafx.fxml;
    opens minigamesPackage to javafx.fxml;
}