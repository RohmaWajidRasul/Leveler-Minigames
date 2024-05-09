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

import gameover.GameOverScreen;

import java.io.IOException;

public class Bolt {

    GraphicsContext gc;
    int RunnerX = 10;
    int RunnerY = 177;
    int RunnerSize = 40;
    final int GridSquare = 20;
    final int FinishLineX = 520;
    final int runnerRows = 5;
    final int gridRow = 20;
    final int gridColumn = 2;
    private int timerValue = 7;
    private GameOverScreen gameOver;

    AccelerationSubscriber accelerationSubscriber;
    Timeline timeline;
    Timeline timer;

    public Bolt(Stage stage){
        start(stage);
    }

    public void start(Stage stage) {
        try {
            accelerationSubscriber = new AccelerationSubscriber();
            accelerationSubscriber.subscribeToMessages();
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
            } catch (MqttException | IOException ex) {
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

    public void run(Stage stage) throws MqttException, IOException {
        drawBackground(gc);
        drawRunner();
        move();
        if (RunnerX > FinishLineX + 50) {
            timeline.stop();
            accelerationSubscriber.unsubscribeFromMessages();
            Game game = new Game(stage);
        } else if (timerValue == 0) {
            Game.level = 0;
            timeline.stop();
            accelerationSubscriber.unsubscribeFromMessages();
            gameOver = new GameOverScreen();
            gameOver.runGameOver(stage);
            /*
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Leveler");
            stage.setScene(scene);
             */
            stage.show();
        }
    }

    public void drawBackground(GraphicsContext gc){
        for (int row = 0; row < runnerRows; row++){
            gc.setFill(Color.rgb(183,43,55));
            gc.fillRect(0,row * 80, 600,75);
        }
        gc.setFill(Color.rgb(183,43,55));
        gc.fillRect(0,395 , 600,5);

        for (int row = 0; row < gridRow; row++){
            for (int column = 0; column < gridColumn; column++){
                if ((row + column) % 2 == 0){
                    gc.setFill(Color.BLACK);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(FinishLineX + (GridSquare * column), row * GridSquare, GridSquare, GridSquare);
            }
        }
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 20));
        gc.fillText(String.format("Time Left: %d",timerValue), 10, 25, 100);

    }

    public void drawRunner(){
        gc.setFill(Color.rgb(30,169,180));
        gc.fillRoundRect(RunnerX, RunnerY, RunnerSize, RunnerSize, 15, 15);
    }

    public void move(){
        if (accelerationSubscriber.getDifference() > (70 + Game.level - 1)) {
            RunnerX = RunnerX + RunnerSize;
        }
    }

}