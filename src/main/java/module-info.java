module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.eclipse.paho.client.mqttv3;


    opens menu to javafx.fxml;
    exports menu;
    exports SequenceGamePackage;
    exports Handlers;
    opens Handlers to javafx.fxml;
}