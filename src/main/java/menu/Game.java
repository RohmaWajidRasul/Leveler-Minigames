package menu;

import SequenceGamePackage.SequenceGame;
import javafx.stage.Stage;

public class Game {

    public static int level = 10;
    private static final int numberOfGames = 2;

    public Game(Stage stage) {
        play(stage);
    }

    private void play(Stage stage) {
        int min = 1;
        int range = numberOfGames - min + 1;

        level++;
        int random = (int) (Math.random() * range) + min;
        switch (2) {
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
