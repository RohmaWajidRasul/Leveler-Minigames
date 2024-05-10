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
    private Text txtLvl;

    public void showlevel() {

        Random randomSplashCaseNewbie = new Random();
        Random randomSplashCasePro = new Random();

        int lowestLimitNewbie = 1;
        int topLimitNewbie = 15; 
        int lowestLimitPro = 16;
        int topLimitPro = 25;
        
        int randomCaseNewbie = randomSplashCaseNewbie.nextInt(topLimitNewbie - lowestLimitNewbie) + lowestLimitNewbie;
        int randomCasePro = randomSplashCasePro.nextInt(topLimitPro - lowestLimitPro) + lowestLimitPro;

        if(Game.level == 1) {
            txtLvl.setText("LEVELER... SERIOUSLY!? ON LEVEL " + Game.level + "...");
        } 
        else if(Game.level >= 2 && Game.level <= 15) {
            switch(randomCaseNewbie) {

                case 1 -> txtLvl.setText(SplashText.GOS_SPLASH_1.getSplashText() + Game.level);
                case 2 -> txtLvl.setText(SplashText.GOS_SPLASH_2.getSplashText() + Game.level);
                case 3 -> txtLvl.setText(SplashText.GOS_SPLASH_3.getSplashText() + Game.level);
                case 4 -> txtLvl.setText(SplashText.GOS_SPLASH_4.getSplashText() + Game.level);
                case 5 -> txtLvl.setText(SplashText.GOS_SPLASH_5.getSplashText() + Game.level);
                case 6 -> txtLvl.setText(SplashText.GOS_SPLASH_6.getSplashText() + Game.level);
                case 7 -> txtLvl.setText(SplashText.GOS_SPLASH_7.getSplashText() + Game.level);
                case 8 -> txtLvl.setText(SplashText.GOS_SPLASH_8.getSplashText() + Game.level);
                case 9 -> txtLvl.setText(SplashText.GOS_SPLASH_9.getSplashText() + Game.level);
                case 10 -> txtLvl.setText(SplashText.GOS_SPLASH_10.getSplashText() + Game.level);
                case 11 -> txtLvl.setText(SplashText.GOS_SPLASH_11.getSplashText() + Game.level);
                case 12 -> txtLvl.setText(SplashText.GOS_SPLASH_12.getSplashText() + Game.level);
                case 13 -> txtLvl.setText(SplashText.GOS_SPLASH_13.getSplashText() + Game.level);
                case 14 -> txtLvl.setText(SplashText.GOS_SPLASH_14.getSplashText() + Game.level);
                case 15 -> txtLvl.setText(SplashText.GOS_SPLASH_15.getSplashText() + Game.level);
            }
        }
        else if (Game.level >= 16 && Game.level <= 25) {
            switch(randomCasePro) {
                case 16 -> txtLvl.setText(SplashText.GOS_SPLASH_16.getSplashText() + Game.level);
                case 17 -> txtLvl.setText(SplashText.GOS_SPLASH_17.getSplashText() + Game.level);
                case 18 -> txtLvl.setText(SplashText.GOS_SPLASH_18.getSplashText() + Game.level);
                case 19 -> txtLvl.setText(SplashText.GOS_SPLASH_19.getSplashText() + Game.level);
                case 20 -> txtLvl.setText(SplashText.GOS_SPLASH_20.getSplashText() + Game.level);
                case 21 -> txtLvl.setText(SplashText.GOS_SPLASH_21.getSplashText() + Game.level);
                case 22 -> txtLvl.setText(SplashText.GOS_SPLASH_22.getSplashText() + Game.level);
                case 23 -> txtLvl.setText(SplashText.GOS_SPLASH_23.getSplashText() + Game.level);
                case 24 -> txtLvl.setText(SplashText.GOS_SPLASH_24.getSplashText() + Game.level);
                case 25 -> txtLvl.setText(SplashText.GOS_SPLASH_25.getSplashText() + Game.level);
            }
        }
        else if(Game.level > 25) {
            txtLvl.setText("LEVELER WENT SUPER SAIYAN UNTIL LEVEL " + Game.level);
        }
    }

    @FXML
    public void restartGame(ActionEvent clickRestart) {
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
