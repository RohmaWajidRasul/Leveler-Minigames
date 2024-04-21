package menu;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttException;

public class BoltMinigameDraft extends Application {

    GraphicsContext gc;
    int RunnerX = 10;
    int RunnerY = 177;
    int RunnerSize = 40;
    final int GridSquare = 20;
    final int FinishLineX = 520;

    final int runnerRows = 5;

    final int gridRow = 20;

    final int gridColumn = 2;

    AccelerationSubscriber accelerationSubscriber;
    Timeline timeline;


    @Override
    public void start(Stage window) {
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

        timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        window.setScene(start);
        window.show();


    }

    public void run(){
        drawBackground(gc);
        drawRunner();
        move();
        if (RunnerX > FinishLineX + 40) {
            timeline.stop();
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
    }

    public void drawRunner(){
        gc.setFill(Color.rgb(30,169,180));
        gc.fillRoundRect(RunnerX, RunnerY, RunnerSize, RunnerSize, 15, 15);
    }

    public void move(){
        if (accelerationSubscriber.getDifference() > 70) {
            RunnerX = RunnerX + RunnerSize;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}