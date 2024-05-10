package gameover;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.Game;

public class GameOverController {
    
    @FXML
    private Text txtLvl;

    public void showlevel() {
        txtLvl.setText("LEVLER DIED ON LEVEL " + Game.level);
    }

    @FXML
    public void restartGame(ActionEvent clickRestart) {
        System.out.println("Replaying game");
        Game.level = 0;
        Node restartNode = (Node) clickRestart.getSource();
        Stage replayStage = (Stage) restartNode.getScene().getWindow();
        Game newGame = new Game(replayStage);
    }

    @FXML
    public void returnToMenu(ActionEvent clickReturnToMenu) throws IOException{
        System.out.println("Returning to Menu");
        Game.level = 0;
        Parent root = FXMLLoader.load(getClass().getResource("/menu/Menu.fxml"));
        Node returnNode = (Node) clickReturnToMenu.getSource();
        Scene menuScene = new Scene(root);
        Stage menuStage = (Stage) returnNode.getScene().getWindow();
        menuStage.setScene(menuScene);
    }
}
