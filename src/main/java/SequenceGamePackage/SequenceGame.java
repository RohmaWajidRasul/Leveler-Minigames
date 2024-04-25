package SequenceGamePackage;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import Handlers.JoystickHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import menu.Game;

public class SequenceGame extends Application {

    private static final int BASE_ARROWS = 3;
    public static int numberOfArrows = BASE_ARROWS + Game.level;
    public static String[] input = new String[numberOfArrows];
    public static boolean gameActive;
    public static String[] strings;
    public static int currentArrow = 0;
    private int seqTime = 10;
    private int seqTimeRemaining = seqTime;
    private int preCountdown = 2;

    public static SequenceGui sequenceGui = new SequenceGui();


    public void start(Stage stage) throws Exception {

        Game(stage);

    }

    public void Game(Stage stage) throws Exception {

        numberOfArrows = BASE_ARROWS + Game.level;

        JoystickHandler joystickHandler = new JoystickHandler();
        joystickHandler.subscribeToMessages();


        sequenceGui.start(stage);

        sequenceGui.drawArrows();
        gameActive = true;

        for(String string : strings) {
            System.out.println(string);
        }

        Text txtTimeDisplay = new Text("Welcome to Sequence!");

        txtTimeDisplay.setStyle("-fx-fill: red; -fx-font-size: 28px; -fx-font-weight: bold; -fx-font-family: 'Courier New';");
        txtTimeDisplay.layoutXProperty();
        
        sequenceGui.stackPane.getChildren().add(txtTimeDisplay);
        sequenceGui.stackPane.setAlignment(txtTimeDisplay, Pos.TOP_CENTER);

        Duration countdownTime = Duration.seconds(1); //increments of seconds 

        Timeline seqTimer = new Timeline(

            new KeyFrame(countdownTime, event -> {

                if(preCountdown > 0) {
                    txtTimeDisplay.setText("MATCH THE ARROW DIRECTION TO WIN");
                    preCountdown = preCountdown - 1;
                } else {
                    txtTimeDisplay.setText("TIME LEFT: " + seqTimeRemaining);

                    if(seqTimeRemaining == 0) {
                        txtTimeDisplay.setText("TIME OUT!");
                    } else {
                        seqTimeRemaining = seqTimeRemaining - 1; 
                    }
                }
            })
        );
        seqTimer.setCycleCount(seqTime + preCountdown + 1);
        seqTimer.play();

    }

    public static String checkPerformance(String directionInput) { //had to make this static for call in MQTT class
        
        String performanceResult;

        if(directionInput.equals(strings[currentArrow])) {
            performanceResult = "arrow_correct"; //correct arrow direction performance
            if(currentArrow == (numberOfArrows - 1)) {
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
                sequenceGui.changeArrowColor(currentArrow, "green");
                currentArrow++;
                break;

            case "quota_met": /* game stops --> display victory --> goes to next game after specified time*/
                sequenceGui.changeArrowColor(currentArrow, "green");
                gameActive = false; /* depends what gameActive means: either the minigame stops or it means the entire continuation of minigames (including others) ends */
                break;

            case "arrow_wrong": /* game stops --> display game over (because of wrong arrow) --> with/without button to go to menu */
                sequenceGui.changeArrowColor(currentArrow, "red");
                sequenceGui.showDisplay();
                gameActive = false;
                currentArrow++; //temp for testing so it continues and doesnt track back
                break;

            case "time_out": /* game stops --> display game over (because of time out) --> with/without button to go to menu */
                sequenceGui.showDisplay();
                gameActive = false;
                break;
        }
    }
}
