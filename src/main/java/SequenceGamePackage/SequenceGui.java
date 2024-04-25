package SequenceGamePackage;

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

    StackPane stackPane;
    Image arrowWhite;
    Image arrowGreen;
    Image arrowRed;
    ImageView imageview1;
    ImageView imageview2;
    ImageView imageview3;
    ImageView imageview4;
    ImageView imageview5;
    ImageView imageview6;
    ImageView imageview7;
    ImageView imageview8;

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

        this.imageview5 = new ImageView(arrowWhite);
        this.imageview5.setImage(arrowWhite);

        this.imageview6 = new ImageView(arrowWhite);
        this.imageview6.setImage(arrowWhite);

        this.imageview7 = new ImageView(arrowWhite);
        this.imageview7.setImage(arrowWhite);

        this.imageview8 = new ImageView(arrowWhite);
        this.imageview8.setImage(arrowWhite);
    }


    public void start(Stage stage) {

        Scene sequenceScene = new Scene(stackPane,600,400);
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
                        case 4 -> imageview5.setRotate(180);
                        case 5 -> imageview6.setRotate(180);
                        case 6 -> imageview7.setRotate(180);
                        case 7 -> imageview8.setRotate(180);
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
                        case 4 -> imageview5.setRotate(270);
                        case 5 -> imageview6.setRotate(270);
                        case 6 -> imageview7.setRotate(270);
                        case 7 -> imageview8.setRotate(270);
                    }

                }
                case 4 -> {
                    strings[i] = "RIGHT";

                    switch(i) {
                        case 0 -> imageview1.setRotate(90);
                        case 1 -> imageview2.setRotate(90);
                        case 2 -> imageview3.setRotate(90);
                        case 3 -> imageview4.setRotate(90);
                        case 4 -> imageview5.setRotate(90);
                        case 5 -> imageview6.setRotate(90);
                        case 6 -> imageview7.setRotate(90);
                        case 7 -> imageview8.setRotate(90);
                    }
                    allLeft = false;
                }
            }
        }
        SequenceGame.strings = strings;



        imageview1.setFitWidth(100);
        imageview1.setPreserveRatio(true);
        imageview1.setTranslateX(-220);

        imageview2.setFitWidth(100);
        imageview2.setPreserveRatio(true);
        imageview2.setTranslateX(-70);

        imageview3.setFitWidth(100);
        imageview3.setPreserveRatio(true);
        imageview3.setTranslateX(80);

        imageview4.setFitWidth(100);
        imageview4.setPreserveRatio(true);
        imageview4.setTranslateX(230);

        System.out.println(imageview1.getTranslateY());



        if (Game.level > 1) {
            imageview1.setTranslateY(100);
            imageview2.setTranslateY(100);
            imageview3.setTranslateY(100);
            imageview4.setTranslateY(100);

            imageview5.setTranslateY(-100);
            imageview5.setFitWidth(100);
            imageview5.setPreserveRatio(true);
            imageview5.setTranslateX(-220);
            stackPane.getChildren().add(imageview5);
        } if (Game.level > 2) {
            imageview6.setTranslateY(-100);
            imageview6.setFitWidth(100);
            imageview6.setPreserveRatio(true);
            imageview6.setTranslateX(-70);
            stackPane.getChildren().add(imageview6);
        } if (Game.level > 3) {
            imageview7.setTranslateY(-100);
            imageview7.setFitWidth(100);
            imageview7.setPreserveRatio(true);
            imageview7.setTranslateX(80);
            stackPane.getChildren().add(imageview7);
        } if (Game.level > 4) {
            imageview8.setTranslateY(-100);
            imageview8.setFitWidth(100);
            imageview8.setPreserveRatio(true);
            imageview8.setTranslateX(230);
            stackPane.getChildren().add(imageview8);
        }


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

        Image arrowColor;

        if(color.equals("green")) {
            arrowColor = arrowGreen;
        } else {
            arrowColor = arrowRed;
        }
        
        if(SequenceGame.gameActive) {

            switch (arrowNumber) {
                case 0 -> this.imageview1.setImage(arrowColor);
                case 1 -> this.imageview2.setImage(arrowColor);
                case 2 -> this.imageview3.setImage(arrowColor);
                case 3 -> this.imageview4.setImage(arrowColor);
                case 4 -> this.imageview5.setImage(arrowColor);
                case 5 -> this.imageview6.setImage(arrowColor);
                case 6 -> this.imageview7.setImage(arrowColor);
                case 7 -> this.imageview8.setImage(arrowColor);
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


