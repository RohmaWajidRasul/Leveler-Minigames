package menu;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttException;

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
    private final String[] colorList = {"Red", "Yellow", "Blue"};

    private final int baseNumOfColors = 5;
    private int currentNumOfColors;

    private Random random;
    private Timeline timeline;

    private boolean gameOver;
    private Stage stage;

    public ColorMatch(Stage stage) throws MqttException, IOException {
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

        random = new Random();
        fontColor = new ArrayList<>();
        writtenColor = new ArrayList<>();
        inputColors = new ArrayList<>();
        currentNumOfColors = baseNumOfColors + Game.level;

        inputIndex = -1;
        gameOver = false;

        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            try {
                run(stage);
            } catch (MqttException | IOException ex) {
                throw new RuntimeException(ex);
            }

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        stage.setScene(start);
        stage.show();

        fillArray(fontColor);
        fillArray(writtenColor);


    }

    public void checkGameCondition() throws MqttException, IOException {
        if (gameOver) {
            lose();
        } else if (inputIndex == currentNumOfColors - 1) {
            nextLevel();
        }
    }

    public void lose() throws MqttException, IOException {
        buttonSubscriber.unsubscribeFromMessages();
        drawScreen(graphicsContext);
        timeline.stop();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, 600, 400);
        System.out.println("GAMEOVER!!!!!!!!!");// replace
        Game.level = 0;
        this.buttonSubscriber.unsubscribeFromMessages();
        backToMenu();
    }

    public void nextLevel() throws MqttException, IOException {
        buttonSubscriber.unsubscribeFromMessages();
        drawScreen(graphicsContext);
        timeline.stop();
        System.out.println("DONE!!!!!!!!!");// replace
        Game game = new Game(stage);
    }

    public void backToMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        this.stage.setTitle("Leveler");
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void checkColorMatch(ArrayList<String> arrayList) {
        if(arrayList.get(inputIndex + 1).equals(inputColors.get(inputIndex + 1))) {
            inputIndex++;
        } else {
            gameOver = true;
        }
    }

    public void getInput() {
        String inputColor = buttonSubscriber.getValidButtonPress();
        if (inputColor != null) {
            inputColors.add(inputIndex + 1, inputColor);
            checkColorMatch(fontColor);
        }

    }

    public void run(Stage stage) throws MqttException, IOException {
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

        for (int i = inputIndex + 1; i < currentNumOfColors; i++) { //use a variable for inputIndex + 1
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
    }

}
