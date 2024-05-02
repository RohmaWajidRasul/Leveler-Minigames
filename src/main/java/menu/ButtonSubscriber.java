package menu;

import org.eclipse.paho.client.mqttv3.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ButtonSubscriber implements MqttCallback{
    private final static ExecutorService THREAD_POOL = Executors.newSingleThreadExecutor();

    private final IMqttClient middleware;
    private final static String TOPIC_YELLOW = "yellow_button_state";
    private final static String TOPIC_RED = "red_button_state";
    private final static String TOPIC_BLUE = "blue_button_state";
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
        } catch (NumberFormatException e) {git
            System.err.println("Received non-integer value, ignoring: " + payloadString);
        }
    }

    public double getValidButtonPress() {
        if (yellowButtonState == 1 && lastYellowButtonState == 0) {
            return 1;
        } else if (redButtonState == 1 && lastRedButtonState == 0) {
            return 1;
        } else if (blueButtonState == 1 && lastBlueButtonState == 0) {
            return 1;
        } else {
            return 0;
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
