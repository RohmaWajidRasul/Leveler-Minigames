package minigamesPackage;
import handlers.AccelerationSubscriber;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import menu.Game;
import org.eclipse.paho.client.mqttv3.MqttException;
import gameover.GameOverScreen;

public class Bolt {

    private GraphicsContext gc;

    private int runnerX;
    private final int runnerY = 177;
    private final int runnerSize = 40;
    private final int gridSquare = 20;
    private final int finishLineX = 520;
    private final int runnerRows = 5;
    private final int gridRow = 20;
    private final int gridColumn = 2;
    private int timerValue;

    private AccelerationSubscriber accelerationSubscriber;
    private Timeline timeline;
    private Timeline timer;
    private GameOverScreen gameOverTrigger;

    public Bolt(Stage stage){
        runnerX = 10;
        timerValue = 7;

        start(stage);
    }

    public void start(Stage stage) {
        try {
            accelerationSubscriber = new AccelerationSubscriber();
            accelerationSubscriber.subscribeToMessages();
            gameOverTrigger = new GameOverScreen();
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1); // Exit if there's an error initializing AccelerationSubscriber
        }

        AnchorPane anchor = new AnchorPane();
        Scene start = new Scene(anchor, 600, 400);

        Canvas canvas = new Canvas(600, 400);
        gc = canvas.getGraphicsContext2D();
        anchor.getChildren().add(canvas);

        timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> {
            try {
                run(stage);
            } catch (Exception  ex) {
                throw new RuntimeException(ex);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> timerValue--));
        timer.setCycleCount(timerValue);
        timer.play();

        stage.setScene(start);
        stage.show();
    }

    public void run(Stage stage) throws Exception {
        drawBackground(gc);
        drawRunner();
        move();
        if (runnerX > finishLineX + 50) {
            timeline.stop();
            accelerationSubscriber.unsubscribeFromMessages();
            Game.play(stage);
        } else if (timerValue == 0) {
            timeline.stop();
            accelerationSubscriber.unsubscribeFromMessages();
            gameOverTrigger.runGameOver(stage);
        }
    }

    public void drawBackground(GraphicsContext gc){
        for (int row = 0; row < runnerRows; row++){
            gc.setFill(Color.rgb(183,43,55));
            gc.fillRect(0,row * 80, 600,75);
        }
        gc.fillRect(0,395 , 600,5);

        for (int row = 0; row < gridRow; row++){
            for (int column = 0; column < gridColumn; column++){
                if ((row + column) % 2 == 0){
                    gc.setFill(Color.BLACK);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(finishLineX + (gridSquare * column), row * gridSquare, gridSquare, gridSquare);
            }
        }
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 20));
        gc.fillText(String.format("Time Left: %d",timerValue), 10, 25, 100);

    }

    public void drawRunner(){
        gc.setFill(Color.rgb(30,169,180));
        gc.fillRoundRect(runnerX, runnerY, runnerSize, runnerSize, 15, 15);
    }

    public void move(){
        if (accelerationSubscriber.getDifference() > (70 + Game.level - 1)) {
            runnerX = runnerX + runnerSize;
        }
    }

}