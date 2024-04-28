package Handlers;

import org.eclipse.paho.client.mqttv3.*;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JoystickHandler implements MqttCallback {

    public int inputNumber = 0;
    public String[] input;
    private final int numberOfArrows;

    // Most of this code is taken from the exercise on canvas

    private final static ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();
    private final IMqttClient middleware;
    private final static String TOPIC   = "SequenceMini-game/Joystick";
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
        // For a more robust system, we should try to re-establish the connection.
        System.out.println("Connection lost!");
        try {
            this.middleware.disconnect();
            this.middleware.close();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        // Try to reestablish? Plan B?
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        //System.out.println("topic '" + topic + "': " + message);
        String s = new String(message.getPayload(), StandardCharsets.UTF_8);
        System.out.println(s);


        if (SequenceGamePackage.SequenceGame.gameActive && this.inputNumber < this.numberOfArrows) {
            this.input[this.inputNumber] = s;
            this.inputNumber++;
        }

    }

    public void unsubscribeFromMessages() throws MqttException {
        middleware.disconnect();
    }

}








