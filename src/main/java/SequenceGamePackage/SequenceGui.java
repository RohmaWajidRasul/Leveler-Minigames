package SequenceGamePackage;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SequenceGui {

    StackPane stackPane;
    Image arrowWhite;
    Image arrowGreen;
    Image arrowRed;
    ImageView imageview1;
    ImageView imageview2;
    ImageView imageview3;
    ImageView imageview4;

    public SequenceGui() {
        this.stackPane = new StackPane();
        this.arrowGreen = new Image("ArrowGreen.png");
        this.arrowRed = new Image("ArrowRed.png");
        this.arrowWhite = new Image("ArrowWhite.png");
        this.imageview1 = new ImageView();
        this.imageview1.setImage(arrowWhite);
        this.imageview2 = new ImageView(arrowWhite);
        this.imageview2.setImage(arrowWhite);
        this.imageview3 = new ImageView();
        this.imageview3.setImage(arrowWhite);
        this.imageview4 = new ImageView();
        this.imageview4.setImage(arrowWhite);
    }


    public void start(Stage stage) throws Exception {




        Polygon arrowHead1 = new Polygon();
        arrowHead1.getPoints().addAll(new Double [] {
                0.0,0.0,
                200.0,0.0,
                100.0,-100.0 });
        arrowHead1.setFill(Color.BLACK);
        arrowHead1.setTranslateX(20);
        arrowHead1.setTranslateY(100);

        Rectangle arrowPart1 = new Rectangle();
        arrowPart1.setHeight(100);
        arrowPart1.setWidth(50);
        arrowPart1.setFill(Color.BLACK);



        //stackPane.getChildren().addAll(arrowPart1);
        //stackPane.getChildren().addAll(arrowHead1);


        Scene sequenceScene = new Scene(stackPane,1000,1000);
        stage.setScene(sequenceScene);
        stage.show();

    }

    public void drawArrows() {
        boolean allLeft = true;


        String[] strings = new String[4];

        int max = 4;
        int min = 1;
        int range = max - min + 1;
        int Randomnumber;

        for (int i = 0; i < strings.length;i++) {
            Randomnumber = (int)(Math.random() * range) + min;

            switch(Randomnumber) {
                case 1 -> {
                    strings[i] = "UP";
                    allLeft = false;
                }

                case 2 -> {
                    strings[i] = "DOWN";

                    switch(i) {
                        case 0 -> imageview1.setRotate(180);
                        case 1 -> imageview2.setRotate(180);
                        case 2 -> imageview3.setRotate(180);
                        case 3 -> imageview4.setRotate(180);
                    }
                    allLeft = false;
                }
                case 3 -> {
                    strings[i] = "LEFT";

                    switch(i) {
                        case 0 -> imageview1.setRotate(270);
                        case 1 -> imageview2.setRotate(270);
                        case 2 -> imageview3.setRotate(270);
                        case 3 -> imageview4.setRotate(270);
                    }

                }
                case 4 -> {
                    strings[i] = "RIGHT";

                    switch(i) {
                        case 0 -> imageview1.setRotate(90);
                        case 1 -> imageview2.setRotate(90);
                        case 2 -> imageview3.setRotate(90);
                        case 3 -> imageview4.setRotate(90);
                    }
                    allLeft = false;
                }

            }

        }
        SequenceGame.strings = strings;



        imageview1.setFitWidth(100);
        imageview1.setPreserveRatio(true);
        imageview1.setTranslateX(-300);

        imageview2.setFitWidth(100);
        imageview2.setPreserveRatio(true);
        imageview2.setTranslateX(-200);


        imageview3.setFitWidth(100);
        imageview3.setPreserveRatio(true);
        imageview3.setTranslateX(-100);

        imageview4.setFitWidth(100);
        imageview4.setPreserveRatio(true);
        imageview4.setTranslateX(0);

        stackPane.getChildren().add(imageview1);
        stackPane.getChildren().add(imageview2);
        stackPane.getChildren().add(imageview3);
        stackPane.getChildren().add(imageview4);


        int position = - 300;

        for (int i = 0; i < strings.length; i++) {
            Text text = new Text(strings[i]);
            text.setTranslateX(position);
            stackPane.getChildren().addAll(text);
            position = position + 100;
        }
    }

    public void changeArrowColor(int arrowNumber, String color) {

        Image arrowColor = arrowWhite;

        if(color == "green") {
            arrowColor = arrowGreen;
        } else {
            arrowColor = arrowRed;
        }
        
        if(SequenceGame.gameActive == true) {

            switch (arrowNumber) {
                case 0 -> this.imageview1.setImage(arrowColor);
                case 1 -> this.imageview2.setImage(arrowColor);
                case 2 -> this.imageview3.setImage(arrowColor);
                case 3 -> this.imageview4.setImage(arrowColor);

            }
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


