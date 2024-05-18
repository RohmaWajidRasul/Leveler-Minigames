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
import java.util.Random;

public class GameOverController {
    
    @FXML
    private Text levelText;

    public void showLevel() {

        Random randomSplashCaseNewbie = new Random();
        Random randomSplashCasePro = new Random();
        GameOverText gameOverString = new GameOverText();

        int newbieSplashTexts = 14; //newbie range set for array with 15 elements, so always subtract 1 from desired range
        int proSplashTexts = 9; //pro range set for array with 10 elements, so always subtract 1 from desired range
        
        int randomCaseNewbie = randomSplashCaseNewbie.nextInt(newbieSplashTexts);
        int randomCasePro = randomSplashCasePro.nextInt(proSplashTexts);

        String textBeingSet;

        if(Game.level == 1) {
            levelText.setText("LEVELER... SERIOUSLY!? ON LEVEL " + Game.level + "...");
        } 
        else if(Game.level <= 15) {
            textBeingSet = gameOverString.setGameOverTextNewbie(randomCaseNewbie);
            levelText.setText(textBeingSet + Game.level);
        }
        else if (Game.level <= 25) {
            textBeingSet = gameOverString.setGameOverTextPro(randomCasePro);
            levelText.setText(textBeingSet + Game.level);
        }
        else {
            levelText.setText("LEVELER WENT SUPER SAIYAN UNTIL LEVEL " + Game.level);
        }
    }

    @FXML
    public void restartGame(ActionEvent clickRestart) throws Exception{
        System.out.println("\nReplaying game . . .");
        Game.level = 0;
        Node restartNode = (Node) clickRestart.getSource();
        Stage replayStage = (Stage) restartNode.getScene().getWindow();
        Game newGame = new Game(replayStage);
    }

    @FXML
    public void returnToMenu(ActionEvent clickReturnToMenu) throws IOException{
        System.out.println("\nReturning to Menu . . .");
        Game.level = 0;
        Parent root = FXMLLoader.load(getClass().getResource("/menu/Menu.fxml"));
        Node returnNode = (Node) clickReturnToMenu.getSource();
        Scene menuScene = new Scene(root);
        Stage menuStage = (Stage) returnNode.getScene().getWindow();
        menuStage.setScene(menuScene);
    }
}
