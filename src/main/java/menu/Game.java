package menu;

import SequenceGamePackage.SequenceGame;
import javafx.stage.Stage;

public class Game {

    public static int level = 0;

    public Game(Stage stage) {
        play(stage);
    }

    public static void play(Stage stage) {
        final int numberOfGames = 2;
        int min = 1;
        int range = numberOfGames - min + 1;

        level++;
        int random = (int) (Math.random() * range) + min;
        switch (random) {
            case 1 ->  {
                Bolt bolt = new Bolt(stage);
            }
            case 2 -> {
                try {
                    SequenceGame sequenceGame = new SequenceGame(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
