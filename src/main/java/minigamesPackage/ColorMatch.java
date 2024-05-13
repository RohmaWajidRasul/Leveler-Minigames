package minigamesPackage;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import handlers.ButtonSubscriber;
import menu.Game;
import menu.Main;
import gameover.GameOverScreen;

import org.eclipse.paho.client.mqttv3.MqttException;

import gameover.GameOverScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ColorMatch {

    private ButtonSubscriber buttonSubscriber;
    private GraphicsContext graphicsContext;

    private ArrayList<String> fontColor;
    private ArrayList<String> writtenColor;
    private ArrayList<String> inputColors;
    private int inputIndex;
    private final String[] colorList;

    private String version;

    private final int baseNumOfColors;
    private int currentNumOfColors;

    private Random random;
    private Timeline timeline;
    private Timeline timer;
    private int timerValue;

    private boolean gameOver;
    private GameOverScreen gameOverTrigger;
    private Stage stage;

    public ColorMatch(Stage stage, String version) throws MqttException, IOException {
        this.colorList = new String[]{"Red", "Yellow", "Blue"};
        this.baseNumOfColors = 4;
        this.timerValue = 15;
        this.version = version;
        this.stage = stage;

        start(stage);
    }

    public void start(Stage stage) throws MqttException {

        AnchorPane anchor = new AnchorPane();
        Scene start = new Scene(anchor, 600, 400);

        Canvas canvas = new Canvas(600, 400);
        graphicsContext = canvas.getGraphicsContext2D();
        anchor.getChildren().add(canvas);

        buttonSubscriber = new ButtonSubscriber();
        buttonSubscriber.subscribeToMessages();
        gameOverTrigger = new GameOverScreen();

        random = new Random();
        fontColor = new ArrayList<>();
        writtenColor = new ArrayList<>();
        inputColors = new ArrayList<>();
        currentNumOfColors = baseNumOfColors + Game.level;

        inputIndex = 0;
        gameOver = false;

        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            try {
                run(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        stage.setScene(start);
        stage.show();

        fillArray(fontColor);
        fillArray(writtenColor);

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> timerValue--));
        timer.setCycleCount(timerValue);
        timer.play();
    }


    public void checkGameCondition() throws Exception {
        if (gameOver || timerValue <= 0) {
            lose();
        } else if (inputIndex == currentNumOfColors) {
            nextLevel();
        }
    }

    public void lose() throws MqttException, IOException {
        drawScreen(graphicsContext);
        timeline.stop();
        timer.stop();
        System.out.println("GAMEOVER!!!!!!!!!");// replace
        this.buttonSubscriber.unsubscribeFromMessages();
        gameOverTrigger.runGameOver(stage);
    }

    public void nextLevel() throws Exception {
        buttonSubscriber.unsubscribeFromMessages();
        drawScreen(graphicsContext);
        timeline.stop();
        timer.stop();
        System.out.println("DONE!!!!!!!!!");// replace
        Game game = new Game(stage);
    }

    public void checkColorMatch(ArrayList<String> arrayList) {
        if(arrayList.get(inputIndex).equals(inputColors.get(inputIndex))) {
            inputIndex++;
        } else {
            gameOver = true;
        }
    }

    public void getInput() {
        String inputColor = buttonSubscriber.getValidButtonPress();
        if (inputColor != null) {
            inputColors.add(inputIndex, inputColor);
            if (version.equals("Colour")) {
                checkColorMatch(fontColor);
            } else if (version.equals("Text")) {
                checkColorMatch(writtenColor);
            }

        }

    }

    public void run(Stage stage) throws Exception {
        drawScreen(graphicsContext);
        getInput();
        checkGameCondition();
    }

    public void fillArray(ArrayList<String> arrayList) {
        for (int i = 0; i < currentNumOfColors; i++) {
            arrayList.add(i, colorList[random.nextInt(colorList.length)]);
        }

    }

    public void drawScreen(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.rgb(138,153,153));
        graphicsContext.fillRect(0, 0, 600, 400);

        double startX = 50;
        double spacing = (600 - 2 * startX) / currentNumOfColors;

        for (int i = inputIndex; i < currentNumOfColors; i++) { //use a variable for inputIndex + 1
            double x = startX + i * spacing;
            double y = 200;

            String text = writtenColor.get(i);
            String color = fontColor.get(i);


            graphicsContext.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 20));
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillText(text, x - 1, y - 1);
            graphicsContext.fillText(text, x + 1, y - 1);
            graphicsContext.fillText(text, x - 1, y + 1);
            graphicsContext.fillText(text, x + 1, y + 1);

            if (color.equals("Red")) {
                graphicsContext.setFill(Color.RED);
            } else if (color.equals("Yellow")) {
                graphicsContext.setFill(Color.YELLOW);
            } else if (color.equals("Blue")) {
                graphicsContext.setFill(Color.BLUE);
            }
            graphicsContext.fillText(text, x, y);
        }

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 20));
        graphicsContext.fillText(String.format("Time Left: %d",timerValue), 10, 25, 100);

        graphicsContext.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 30));
        if (version.equals("Colour")) {
            String versionText = "Match displayed color";
            Text text = new Text(versionText);
            text.setFont(graphicsContext.getFont());
            double textWidth = text.getLayoutBounds().getWidth();
            double centerX = (600 - textWidth) / 2;
            graphicsContext.fillText(versionText, centerX, 120, 300);

        } else if (version.equals("Text")) {
            String versionText = "Match text";
            Text text = new Text(versionText);
            text.setFont(graphicsContext.getFont());
            double textWidth = text.getLayoutBounds().getWidth();
            double centerX = (600 - textWidth) / 2;
            graphicsContext.fillText(versionText, centerX, 120, 300);
        }
    }


}
