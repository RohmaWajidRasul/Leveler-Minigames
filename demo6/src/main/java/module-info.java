module com.example.demo6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.eclipse.paho.client.mqttv3;


    opens com.example.demo6 to javafx.fxml;
    exports com.example.demo6;
}