package menu;

import javafx.stage.Stage;

public class Game {

    public static int level = 0;
    private static final int numberOfGames = 1;

    public Game(Stage stage){
        play(stage);
    }

    private void play(Stage stage){
        level++;
        int random = (int) (Math.random() * (numberOfGames - 1));
        switch (random){
            case 0 ->  {Bolt bolt = new Bolt(stage);}
            //case 1 ->
        }
    }

}
