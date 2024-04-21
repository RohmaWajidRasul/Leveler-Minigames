package SequenceGamePackage;

import java.util.Timer;
import java.util.TimerTask;

import Handlers.JoystickHandler;

import javafx.application.Application;
import javafx.stage.Stage;

public class SequenceGame  {
    public static String[] input = new String[4];
    public static boolean gameActive;
    public static int number = 0;
    public static String[] strings;
    public static int count = 0;

    private int timeStamp = 7; //total countdown
    private int timeZero = 0; //seconds

    public static SequenceGui sequenceGui = new SequenceGui();


    public void begin(Stage stage) throws Exception {

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

        Timer gameTimer = new Timer();

        gameTimer.schedule(new TimerTask() {
            
            public void run() {
                if (timeStamp >= timeZero) {
                    System.out.println(timeStamp);
                    //lbTimer.setText("TIMER COUNTDOWN");
                    timeStamp--;

                } else {
                    managePerformance("time_out");
                    gameTimer.cancel();
                }
            }

        }, 0,1000); //1000 = 1000 milliseconds = 1 second

    }

    public static String checkPerformance(String directionInput) { //had to make this static for call in MQTT class
        
        String performanceResult;   
        int arrowQuota = 4; //this is for how many arrows should be done correctly to win

        if(directionInput.equals(strings[count])) {
            performanceResult = "arrow_correct"; //correct arrow direction performance
            if(count == (arrowQuota - 1)) {
                performanceResult = "quota_met";
            }
        } else {
            performanceResult = "arrow_wrong"; //incorrect arrow direction performance --> game over
        }
        return performanceResult; /* time out is checked in the Game method within timer background loop */
    }
}
