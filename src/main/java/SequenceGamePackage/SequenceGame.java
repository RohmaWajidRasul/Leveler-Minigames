package SequenceGamePackage;

import java.util.Timer;
import java.util.TimerTask;

import Handlers.JoystickHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class SequenceGame  {
    public static String[] input = new String[4 + SequenceGui.level - 1];
    public static boolean gameActive;
    public static int number = 0;
    public static String[] strings;
    public static int count = 0;
    public static int ArrowCount = 0;

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

    public static void managePerformance(String performanceResult) { //had to make this static for call in MQTT class

        System.out.println(performanceResult);
        switch (performanceResult) {

            case "arrow_correct": /* input correct arrow counted --> (possibly visual/auditory feedback) --> game continues on to next arrow */
                gameActive = true;
                sequenceGui.changeArrowColor(count, "green");
                count++;
                break;

            case "quota_met": /* game stops --> display victory --> goes to next game after specified time*/
                sequenceGui.changeArrowColor(count, "green");
                gameActive = false; /* depends what gameActive means: either the minigame stops or it means the entire continuation of minigames (including others) ends */
                break;

            case "arrow_wrong": /* game stops --> display game over (because of wrong arrow) --> with/without button to go to menu */
                sequenceGui.changeArrowColor(count, "red");
                sequenceGui.showDisplay();
                gameActive = false;
                count++; //temp for testing so it continues and doesnt track back
                break;

            case "time_out": /* game stops --> display game over (because of time out) --> with/without button to go to menu */
                sequenceGui.showDisplay();
                gameActive = false;
                break;
        }
    }
}
