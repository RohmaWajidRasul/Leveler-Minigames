package SequenceGamePackage;

import Handlers.JoystickHandler;

import javafx.application.Application;
import javafx.stage.Stage;

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

        JoystickHandler joystickHandler = new JoystickHandler();
        joystickHandler.subscribeToMessages();


        sequenceGui.start(stage);

        //strings = generateArrowDirection();
        sequenceGui.drawArrows();
        gameActive = true;

        for(String string : strings) {
            System.out.println(string);
        }



    }

    public static boolean checkDirection(String dierection) {

        boolean result = false;
        if (count < strings.length) {
            if (dierection.equals(strings[count])) {
                result = true;
                sequenceGui.changeArrowColor(count);

            } else {
                gameActive = false;
            }

            count++;
        } else {
            gameActive = false;
            result = false;
        }

        return result;
    }
}
