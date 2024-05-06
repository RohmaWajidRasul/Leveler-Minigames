package menu;
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
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ColorMatch {

    private ButtonSubscriber buttonSubscriber;
    private GraphicsContext graphicsContext;

    private ArrayList<String> textColor;
    private ArrayList<String> writtenColor;
    private String[] colorlist;

    private int numOfColors;

    private Random random;
    private Timeline timeline;



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

        random = new Random();

        textColor = new ArrayList<>();
        writtenColor = new ArrayList<>();

        numOfColors = 5;
        colorlist = new String[]{"Red", "Yellow", "Blue"};

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

        fillArray(textColor);
        fillArray(writtenColor);


    }


    public void run(Stage stage) throws MqttException, IOException {
        drawScreen(graphicsContext);

    }

    public void fillArray(ArrayList<String> arrayList) {
        for (int i = 0; i < numOfColors; i++) {
            arrayList.add(i, colorlist[random.nextInt(colorlist.length)]);
        }

    }

    public void drawScreen(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.rgb(138,153,153));
        graphicsContext.fillRect(0, 0, 600, 400);

        double startX = 50;
        double spacing = (600 - 2 * startX) / numOfColors;

        for (int i = 0; i < numOfColors; i++) { //use a variable for inputIndex + 1
            double x = startX + i * spacing;
            double y = 200;

            String text = writtenColor.get(i);
            String color = textColor.get(i);


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
