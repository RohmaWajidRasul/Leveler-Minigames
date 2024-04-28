package SequenceGamePackage;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.Game;

public class SequenceGui {

    private ArrayList <ImageView> arrowViewArrL; //scalable array lists for arrow drawing
    public ArrayList <String> arrowOrientationArrL;

    private int scrollSpeed;
    public StackPane stackPane;
    Image arrowWhite;
    Image arrowGreen;
    Image arrowRed;
    Image arrowGrey;

    public SequenceGui() {
        this.stackPane = new StackPane();
        this.arrowGreen = new Image(getClass().getResourceAsStream("/images/ArrowGreen.png"));
        this.arrowRed = new Image(getClass().getResourceAsStream("/images/ArrowRed.png"));
        this.arrowWhite = new Image(getClass().getResourceAsStream("/images/ArrowWhite.png"));
        this.arrowGrey = new Image(getClass().getResourceAsStream("/images/ArrowGrey.png")); //grey for alternative default color option 

        arrowViewArrL = new ArrayList<>();
        arrowOrientationArrL = new ArrayList<>();
        this.scrollSpeed = 15; //change arrow sequence speed

    }


    public void start(Stage stage) {

        Scene sequenceScene = new Scene(stackPane,600,400);
        stage.setScene(sequenceScene);
        stage.show();

    }

    public void drawArrows(int numberOfArrows) {
        int arrowPlacing = 225; //arrow starting x-pos
        int arrowSpacing = 170;
        int arrowSizeWH = 125;

        int max = 4;
        int min = 1;
        int range = max - min + 1;

        for(int i = 0; i < numberOfArrows; i++) { //this loop calls the method that creates the arrows and stores them


            int randomNumber = (int) (Math.random() * range) + min;
            int orientationDegree = randomNumber * 90;
            String orientation = "UP";

            switch (orientationDegree) {
                case 0 -> orientation = "UP";
                case 90 -> orientation = "RIGHT";
                case 180 -> orientation = "DOWN";
                case 270 -> orientation = "LEFT";
            }

            ImageView arrowView = new ImageView();
            arrowView.setImage(arrowGrey);

            arrowView.setTranslateX(arrowPlacing);
            arrowView.setFitWidth(arrowSizeWH);
            arrowView.setFitHeight(arrowSizeWH);
            arrowView.setRotate(orientationDegree);

            stackPane.getChildren().add(arrowView);
            arrowViewArrL.add(arrowView);
            arrowOrientationArrL.add(orientation);

            arrowPlacing = arrowPlacing + arrowSpacing;
        }
        egg();
    }

    public void moveArrows() { //timeline for moving arrow sequence to the left by accessing ArrayList

        Timeline arrowMovementTL = new Timeline(new KeyFrame(Duration.millis(scrollSpeed), event -> {

            for (ImageView arrowView : arrowViewArrL) {
                double arrowPosition = arrowView.getTranslateX();
                arrowPosition--;
                arrowView.setTranslateX(arrowPosition);
            }
        }));
        arrowMovementTL.setCycleCount(Timeline.INDEFINITE);
        arrowMovementTL.play();
    }

    public void changeArrowColor(int arrowNumber, String color) {

        Image arrowColor;
    
        if(color.equals("green")) {
            arrowColor = arrowGreen;
        } else {
            arrowColor = arrowRed;
        }
        
        if(SequenceGame.gameActive) {
            ImageView specificArrowView = arrowViewArrL.get(arrowNumber);
            specificArrowView.setImage(arrowColor);
        }
    }

    private void egg() {

        boolean allLeft = true;
        boolean allUp = true;
        boolean allDown = true;
        boolean allRight = true;

        for (String string : arrowOrientationArrL) {
            switch (string) {
                case "DOWN" -> {
                    allLeft = false;
                    allUp = false;
                    allRight = false;
                }
                case "UP" -> {
                    allLeft = false;
                    allRight = false;
                    allDown = false;

                }
                case "LEFT" -> {
                    allDown = false;
                    allUp = false;
                    allRight = false;
                }
                case "RIGHT" -> {
                    allLeft = false;
                    allDown = false;
                    allUp = false;
                }
            }
        }
        Text text = new Text();
        text.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: black;");
        stackPane.getChildren().add(text);

        if (allLeft) { //Homage to the queen
            text.setText("To the left, to the left\n everything you own in the box to the left");
        } else if (allRight) {
            text.setText("Right where you left me");
        } else if (allUp) {
            text.setText("We're going u-u-u-u-u-u-up");
        } else if (allDown) {
            text.setText("Now I'm down bad crying at the gym");
        }

        if(Game.level < 2) {
            text.setTranslateY(-100);
        }
    }

    public void showDisplay() { //to prevent thread overload issues
        Platform.runLater(this::showGameOver); 
    }

    public void showGameOver() { //to display GAME OVER
        Label lbGameOver = new Label("GAME OVER");
        stackPane.getChildren().add(lbGameOver);
        lbGameOver.setTranslateY(-200);

        lbGameOver.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: red;");
    }
}


