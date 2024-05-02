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
    public static boolean gameActive;
    private final int numberOfArrows;
    private int currentArrow;
    private final int seqTime;
    private int seqTimeRemaining;
    private int preCountdown;
    private final SequenceGui sequenceGui;
    private final JoystickHandler joystickHandler;
    private Timeline timeline;
    private Timeline seqTimer;
    private final Stage stage;
    private boolean colorChange;


    public SequenceGame(Stage stage) throws Exception {
        int baseArrowAmount = 3;
        this.currentArrow = 0;
        this.numberOfArrows = baseArrowAmount + Game.level;
        this.seqTime = 10;
        this.seqTimeRemaining = seqTime;
        this.preCountdown = 2;
        this.sequenceGui = new SequenceGui();
        this.joystickHandler = new JoystickHandler(this.numberOfArrows);
        this.joystickHandler.subscribeToMessages();
        this.stage = stage;
        colorChange = false;
        Game(stage);

    }

    public void Game(Stage stage) throws Exception {


        this.sequenceGui.start(stage);

        this.sequenceGui.drawArrows(this.numberOfArrows);
        this.sequenceGui.moveArrows(this.numberOfArrows); //starts timeline to move arrows to the left

        gameActive = true;

        Duration updateInterval = Duration.millis(100);

        this.timeline = new Timeline( new KeyFrame(updateInterval, event -> {

            if (this.joystickHandler.getInput(currentArrow) != null && this.currentArrow < this.numberOfArrows) {

                String performanceResult;

                String userInput = this.joystickHandler.getInput(currentArrow);
                String currentOrientation = this.sequenceGui.getArrowOrientationList().get(currentArrow); //string with arrow orientation from ArrayList

                if(userInput.equals(currentOrientation) && this.currentArrow == (this.numberOfArrows - 1)) {
                    if(this.currentArrow == (this.numberOfArrows - 1)) {
                        performanceResult = "quota_met";
                        sequenceGui.changeArrowColor(currentArrow, "green");

                        if (colorChange) {
                            managePerformance(performanceResult);
                        }
                        colorChange = true;

                    }
                } else if(userInput.equals(currentOrientation)) {
                    performanceResult = "arrow_correct"; //correct arrow direction performance
                    managePerformance(performanceResult);

                } else {

                    performanceResult = "arrow_wrong";
                    sequenceGui.changeArrowColor(currentArrow, "red");

                    if (colorChange) {
                        managePerformance(performanceResult);
                    }
                    colorChange = true;
                }


            }

        }));
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();

        Text txtTimeDisplay = new Text();
        if(Game.level == 1) {
            txtTimeDisplay.setText("Welcome to Sequence!");
        } else {
            txtTimeDisplay.setText("LEVEL: " + Game.level);
        }

        txtTimeDisplay.setStyle("-fx-fill: red; -fx-font-size: 28px; -fx-font-weight: bold; -fx-font-family: 'Courier New';");
        txtTimeDisplay.layoutXProperty();
        
        this.sequenceGui.stackPane.getChildren().add(txtTimeDisplay);
        this.sequenceGui.stackPane.setAlignment(txtTimeDisplay, Pos.TOP_CENTER);

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

                clearLevel();
                break;

            case "arrow_wrong": /* game stops --> display game over (because of wrong arrow) --> with/without button to go to menu */

                sequenceGui.showDisplay();
                gameActive = false;
                lose();
                break;

            case "time_out": /* game stops --> display game over (because of time out) --> with/without button to go to menu */
                sequenceGui.showDisplay();
                gameActive = false;
                System.out.println("Losing him was blue, like i'd never known");
                lose();
                break;
        }
    }

    public void lose() {
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

    public void clearLevel() {
        gameActive = false;

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
