package handlers;

import org.eclipse.paho.client.mqttv3.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JoystickHandler implements MqttCallback {
    //https://canvas.gu.se/courses/72527/files/folder/Code%20Examples/MQTT%20Java
    //Code based on example code from canvas

    private int inputNumber = 0;
    private String[] input;
    private final int numberOfArrows;

    private final static ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();
    private final IMqttClient middleware;

    private final static String TOPIC   = "sensor/direction";
    private final static String BROKER  = "tcp://test.mosquitto.org:1883";
    private final static String USER_ID = "Group-14";

    public JoystickHandler(int numberOfArrows) throws MqttException {
        this.numberOfArrows = numberOfArrows;
        this.input = new String[numberOfArrows];

        this.middleware = new MqttClient(BROKER, USER_ID);
        this.middleware.connect();
        this.middleware.setCallback(this);
    }

    public void subscribeToMessages() {
        // This is an example of a thread that will continue running until the program stops.
        THREAD_POOL.submit(() -> {
            try {

                this.middleware.subscribe(TOPIC);

            } catch (MqttSecurityException e) {
                e.printStackTrace();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void connectionLost(Throwable throwable) {
        // Simple code to indicate that the connection was lost.
        System.out.println("Connection lost!");
        try {
            this.middleware.disconnect();
            this.middleware.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        //System.out.println("topic '" + topic + "': " + message);
        String s = new String(message.getPayload(), StandardCharsets.UTF_8);
        System.out.println(s);

        if ( this.inputNumber < this.numberOfArrows) {
            this.input[this.inputNumber] = s;
            this.inputNumber++;
        }
    }

    public void unsubscribeFromMessages() throws MqttException {
        this.middleware.disconnect();
    }

    public String getInput(int index) {
        return this.input[index];
    }
}








