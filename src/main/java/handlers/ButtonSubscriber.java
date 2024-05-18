package handlers;

import org.eclipse.paho.client.mqttv3.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ButtonSubscriber implements MqttCallback{
    private final static ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();

    private final IMqttClient middleware;
    private final static String TOPIC_YELLOW = "sensor/yellow_button_state";
    private final static String TOPIC_RED = "sensor/red_button_state";
    private final static String TOPIC_BLUE = "sensor/blue_button_state";
    private final static String BROKER  = "tcp://test.mosquitto.org";
    private final static String USER_ID = "";
    private int yellowButtonState = 1;
    private int lastYellowButtonState = 1;
    private int redButtonState = 1;
    private int lastRedButtonState = 1;
    private int blueButtonState = 1;
    private int lastBlueButtonState = 1;

    public ButtonSubscriber() throws MqttException {
        middleware = new MqttClient(BROKER, USER_ID);
        middleware.connect();
        middleware.setCallback(this);
    }

    public void subscribeToMessages() {
        THREAD_POOL.submit(() -> {
            try {
                middleware.subscribe(TOPIC_YELLOW);
                middleware.subscribe(TOPIC_RED);
                middleware.subscribe(TOPIC_BLUE);

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
            int intValue = Integer.parseInt(payloadString);
            if (topic.equals(TOPIC_YELLOW)) {
                lastYellowButtonState = yellowButtonState;
                yellowButtonState = intValue;

            } else if (topic.equals(TOPIC_RED)) {
                lastRedButtonState = redButtonState;
                redButtonState = intValue;

            } else if (topic.equals(TOPIC_BLUE)) {
                lastBlueButtonState = blueButtonState;
                blueButtonState = intValue;

            }
        } catch (NumberFormatException e) {
            System.err.println("Received non-integer value, ignoring: " + payloadString);
        }
    }

    public String getValidButtonPress() {
        if (yellowButtonState == 0 && lastYellowButtonState == 1) {
            lastYellowButtonState = 0;
            return "Yellow";

        } else if (redButtonState == 0 && lastRedButtonState == 1) {
            lastRedButtonState = 0;
            return "Red"; //Loving him was read

        } else if (blueButtonState == 0 && lastBlueButtonState == 1) {
            lastBlueButtonState = 0;
            return "Blue"; //Losing him was blue, like I'd never know
        } else {
            return null;
        }
    }

    public void unsubscribeFromMessages() throws MqttException {
        middleware.disconnect();
    }

    public static void main(String[] args) throws MqttException {
        ButtonSubscriber buttonSubscriber = new ButtonSubscriber();
        buttonSubscriber.subscribeToMessages();
    }
}
