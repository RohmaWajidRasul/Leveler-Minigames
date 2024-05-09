package gameover;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import menu.Game;

public class GameOverController {
    
    @FXML
    private Text txtLvl;

    public void showlevel() {
        txtLvl.setText("ACHIEVED LEVEL " + Game.level);
    }

    @FXML
    public void restartGame(ActionEvent clickReplay) {
        System.out.println("Replaying game");

    }

    @FXML
    public void returnToMenu(ActionEvent clickReturnHome) throws IOException{
        System.out.println("Returning to Menu");

    }
}
