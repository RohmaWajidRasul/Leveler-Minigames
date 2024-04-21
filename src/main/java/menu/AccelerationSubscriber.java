package menu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class AccelerationSubscriber implements MqttCallback {

    private final static ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();

    // These are important variables that you need to change.
    private final IMqttClient middleware;
    private final static String TOPIC   = "Topic 1";
    private final static String BROKER  = "tcp://test.mosquitto.org";
    private final static String USER_ID = "";

    public AccelerationSubscriber() throws MqttException {
        middleware = new MqttClient(BROKER, USER_ID);
        middleware.connect();
        middleware.setCallback(this);
    }

    private void subscribeToMessages() {
        // This is an example of a thread that will continue running until the program stops.
        THREAD_POOL.submit(() -> {
            try {

                middleware.subscribe(TOPIC);

            } catch (MqttSecurityException e) {
                e.printStackTrace();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });
    }

    // These methods are the implementation of methods that will be triggered when
    // different events for *this* MQTT client.
    @Override
    public void connectionLost(Throwable throwable) {
        // Simple code to indicate that the connection was lost.
        // For a more robust system, we should try to re-establish the connection.
        System.out.println("Connection lost!");
        try {
            middleware.disconnect();
            middleware.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        // Try to reestablish? Plan B?
    }

    // This method would be used when we know that the message was delivered. Its important
    // when handling more synchronised communication using higher "quality of services" (QoS)
    // You will learn about QoS in a future course.
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payloadString = new String(message.getPayload());

        // Check if the payload can be parsed into a float
        try {
            double doubleValue = Double.parseDouble(payloadString);
            // Now you have the float value
            System.out.println("Received double value: " + doubleValue);
        } catch (NumberFormatException e) {
            // Payload is not a valid float, so ignore it
            System.err.println("Received non-double value, ignoring: " + payloadString);
        }
    }

    public static void main(String[] args) throws MqttException {
        AccelerationSubscriber accelerationSubscriber = new AccelerationSubscriber();
        accelerationSubscriber.subscribeToMessages();
    }
}