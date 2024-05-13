package menu;

import minigamesPackage.Bolt;
import minigamesPackage.ColorMatch;
import minigamesPackage.SequenceGame;
import javafx.stage.Stage;

public class Game {

    public static int level = 0;

    public Game(Stage stage) throws Exception{
        play(stage);
    }

    public static void play(Stage stage) throws Exception {
        final int numberOfGames = 4;
        int min = 1;
        int range = numberOfGames - min + 1;

        level++;
        int random = (int) (Math.random() * range) + min;
        switch (3) {
            case 1 ->  {Bolt bolt = new Bolt(stage);}
            case 2 -> {SequenceGame sequenceGame = new SequenceGame(stage);}
            case 3 ->  {ColorMatch colorMatch = new ColorMatch(stage, "Colour");}
            case 4 ->  {ColorMatch colorMatch = new ColorMatch(stage, "Text");}
        }
    }

}
