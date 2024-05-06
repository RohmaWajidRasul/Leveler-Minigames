package menu;

import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;

public class Game {

    public static int level = 0;
    private static final int numberOfGames = 1;

    public Game(Stage stage) throws MqttException, IOException {
        play(stage);
    }

    private void play(Stage stage) throws MqttException, IOException {
        level++;
        int random = (int) (Math.random() * (numberOfGames - 1));
        switch (2){
            case 0 ->  {Bolt bolt = new Bolt(stage);}
            case 2 ->  {ColorMatch colorMatch = new ColorMatch(stage);}

        }
    }

}
