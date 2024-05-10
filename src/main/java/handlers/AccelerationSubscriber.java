package handlers;

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

    private final IMqttClient middleware;
    private final static String TOPIC   = "sensor/vibration";
    private final static String BROKER  = "tcp://test.mosquitto.org";
    private final static String USER_ID = "";
    private double zValue = 0.9;
    private double lastZValue = 0.9;

    public AccelerationSubscriber() throws MqttException {
        middleware = new MqttClient(BROKER, USER_ID);
        middleware.connect();
        middleware.setCallback(this);
    }

    public void subscribeToMessages() {
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


    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection lost!");
        try {
            middleware.disconnect();
            middleware.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    public void messageArrived(String topic, MqttMessage message) {
        String payloadString = new String(message.getPayload());

        try {
            double doubleValue = Double.parseDouble(payloadString);
            // Update zValue and lastZValue
            lastZValue = zValue;
            zValue = doubleValue;
            // Now you have the float value
            System.out.println("last" + lastZValue);
            System.out.println("z" + zValue);
            System.out.println((Math.abs(lastZValue - doubleValue) / 3) * 100);
        } catch (NumberFormatException e) {
            // Payload is not a valid float, so ignore it
            System.err.println("Received non-double value, ignoring: " + payloadString);
        }
    }

    public double getDifference() {
        return (Math.abs(lastZValue - zValue)) * 100;
    }

    public void unsubscribeFromMessages() throws MqttException {
        middleware.disconnect();
    }

    public static void main(String[] args) throws MqttException {
        AccelerationSubscriber accelerationSubscriber = new AccelerationSubscriber();
        accelerationSubscriber.subscribeToMessages();
    }
}