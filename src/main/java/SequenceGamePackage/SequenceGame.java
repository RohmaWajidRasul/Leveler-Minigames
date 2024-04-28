package SequenceGamePackage;

import javafx.animation.Animation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import Handlers.JoystickHandler;
import javafx.stage.Stage;
import menu.Game;
import menu.Main;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;

public class SequenceGame {

    private static final int BASE_ARROWS = 3;
    public static int numberOfArrows = BASE_ARROWS + Game.level;
    public static boolean gameActive;
    public static String[] correctStrings;
    private int currentArrow = 0;
    private int seqTime = 10;
    private int seqTimeRemaining = seqTime;
    private int preCountdown = 2;
    private SequenceGui sequenceGui;
    private JoystickHandler joystickHandler;
    private Timeline timeline;
    private Timeline seqTimer;

    private Stage stage;


    public SequenceGame(Stage stage) throws Exception {


        if (Game.level > 5) {
            numberOfArrows = 8;
        } else {
            numberOfArrows = BASE_ARROWS + Game.level;
        }

        this.sequenceGui = new SequenceGui();
        joystickHandler = new JoystickHandler();
        joystickHandler.subscribeToMessages();
        this.stage = stage;
        Game(stage);

    }

    public void Game(Stage stage) throws Exception {

        sequenceGui.start(stage);

        sequenceGui.drawArrows();
        sequenceGui.moveArrows(); //starts timeline to move arrows to the left

        gameActive = true;

        Duration updateInterval = Duration.millis(100);

        this.timeline = new Timeline( new KeyFrame(updateInterval, event -> {

            if (joystickHandler.input[currentArrow] != null && currentArrow < numberOfArrows) {

                String performanceResult;

                String banana = joystickHandler.input[currentArrow];
                String currentOrientation = sequenceGui.arrowOrientationArrL.get(currentArrow); //string with arrow orientation from ArrayList

                if(banana.equals(currentOrientation)) { //this compares the orientation of the arraylist arrows instead of the commented-out comparison in the line above (for now)
                    performanceResult = "arrow_correct"; //correct arrow direction performance
                    if(currentArrow == (numberOfArrows - 1)) {
                        performanceResult = "quota_met";
                    }
                } else {

                    performanceResult = "arrow_wrong"; //incorrect arrow direction performance --> game over

                }
                 managePerformance(performanceResult);

            }

        }));
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();

        Text txtTimeDisplay = new Text("Welcome to Sequence!");

        txtTimeDisplay.setStyle("-fx-fill: red; -fx-font-size: 28px; -fx-font-weight: bold; -fx-font-family: 'Courier New';");
        txtTimeDisplay.layoutXProperty();
        
        sequenceGui.stackPane.getChildren().add(txtTimeDisplay);
        sequenceGui.stackPane.setAlignment(txtTimeDisplay, Pos.TOP_CENTER);

        Duration countdownTime = Duration.seconds(1); //increments of seconds 

        seqTimer = new Timeline(

            new KeyFrame(countdownTime, event -> {

                if(preCountdown > 0) {
                    txtTimeDisplay.setText("MATCH THE ARROW DIRECTION TO WIN");
                    preCountdown = preCountdown - 1;
                } else {
                    txtTimeDisplay.setText("TIME LEFT: " + seqTimeRemaining);

                    if(seqTimeRemaining == 0) {

                        txtTimeDisplay.setText("TIME OUT!");
                        managePerformance("time_out");
                    } else {
                        seqTimeRemaining = seqTimeRemaining - 1; 
                    }
                }
            })
        );
        seqTimer.setCycleCount(seqTime + preCountdown + 1);
        seqTimer.play();

    }

    public void managePerformance(String performanceResult) { //had to make this static for call in MQTT class

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
                myDaddySaidShoot();
                break;

            case "arrow_wrong": /* game stops --> display game over (because of wrong arrow) --> with/without button to go to menu */
                sequenceGui.changeArrowColor(currentArrow, "red");
                sequenceGui.showDisplay();
                gameActive = false;
                die();
                break;

            case "time_out": /* game stops --> display game over (because of time out) --> with/without button to go to menu */
                sequenceGui.showDisplay();
                gameActive = false;
                die();


                break;
        }
    }

    public void die() {
        this.timeline.stop();
        this.seqTimer.stop();
        Game.level = 0;
        try {
            joystickHandler.unsubscribeFromMessages();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Leveler");
            stage.setScene(scene);
            stage.show();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void myDaddySaidShoot() {

        this.timeline.stop();
        this.seqTimer.stop();
        try {
            joystickHandler.unsubscribeFromMessages();
            Game game = new Game(stage);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

}
