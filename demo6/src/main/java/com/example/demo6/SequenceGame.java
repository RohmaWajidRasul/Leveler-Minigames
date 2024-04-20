package com.example.demo6;

import javafx.application.Application;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

public class SequenceGame extends Application {
    public static String[] input = new String[4];
    public static boolean gameActive;
    public static int number = 0;
    public static String[] strings;
    public static int count = 0;

    public static SequenceGui sequenceGui = new SequenceGui();

    @Override
    public void start(Stage stage) throws Exception {

        Game(stage);

    }

    public void Game(Stage stage) throws Exception {

        MQTT MQTT = new MQTT();
        MQTT.subscribeToMessages();

        SequenceGui sequenceGui = new SequenceGui();
        sequenceGui.start(stage);

        strings = generateArrowDirection();
        sequenceGui.drawArrows(strings);
        gameActive = true;

        for(String string : strings) {
            System.out.println(string);
        }



    }

    public static boolean checkDirection(String dierection) {

        boolean rename = false;
        if (count < strings.length) {
            if (dierection.equals(strings[count])) {
                rename = true;
                sequenceGui.changeArrowColor(count);

            }

            count++;
        } else {
            gameActive = false;
            rename = false;
        }

        return rename;
    }



    public String[] generateArrowDirection() {


        String[] strings = new String[4];

        int max = 4;
        int min = 1;
        int range = max - min + 1;
        int number;

        for (int i = 0; i < strings.length;i++) {
            number = (int)(Math.random() * range) + min;

            switch(number) {
                case 1 -> strings[i] = "UP";
                case 2 -> strings[i] = "DOWN";
                case 3 -> strings[i] = "LEFT";
                case 4 -> strings[i] = "RIGHT";

            }

        }
        return strings;
    }

}
