package SequenceGamePackage;

import Handlers.JoystickHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class SequenceGame  {
    public static String[] input = new String[4 + SequenceGui.level - 1];
    public static boolean gameActive;
    public static int number = 0;
    public static String[] strings;
    public static int ArrowCount = 0;

    public static SequenceGui sequenceGui = new SequenceGui();


    public void begin(Stage stage) throws Exception {

        Game(stage);

    }

    public void Game(Stage stage) throws Exception {

        JoystickHandler joystickHandler = new JoystickHandler();
        joystickHandler.subscribeToMessages();


        sequenceGui.start(stage);

        sequenceGui.drawArrows();
        gameActive = true;

        for(String string : strings) {
            System.out.println(string);
        }

    }



    public static boolean checkDirection(String dierection) {

        boolean result = false;
        if (ArrowCount < strings.length) {
            if (dierection.equals(strings[ArrowCount])) {
                result = true;
                sequenceGui.changeArrowColor(ArrowCount);

            } else {
                gameActive = false;


            }

            ArrowCount++;
        } else {
            gameActive = false;
            result = false;
        }

        return result;
    }
}
