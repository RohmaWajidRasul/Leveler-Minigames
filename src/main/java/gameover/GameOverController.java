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

        System.out.println("\nNOOB LEVELS: " + randomCaseNewbie);
        System.out.println("\nPRO LEVELS: " + randomCasePro);

        if(Game.level == 1) {
            txtLvl.setText("LEVELER... SERIOUSLY!? ON LEVEL " + Game.level + "...");
        } 
        else if(Game.level >= 2 && Game.level <= 15) {
            switch(randomCaseNewbie) {

                case 1 -> txtLvl.setText("LEVELER MET THE INEVITABLE ON LEVEL "+ Game.level);
                case 2 -> txtLvl.setText("UNREMARKABLY, LEVELER DIED ON LEVEL " + Game.level);
                case 3 -> txtLvl.setText("LEVELER GOT COOKED ON LEVEL " + Game.level);
                case 4 -> txtLvl.setText("LEVELER ALMOST PROVED A POINT ON LEVEL " + Game.level);
                case 5 -> txtLvl.setText("LEVELER BARELY TRIED TO SURVIVE LEVEL " + Game.level);
                case 6 -> txtLvl.setText("LEVELER CHOSE ALT-F4 ON LEVEL " + Game.level);
                case 7 -> txtLvl.setText("LEVELER LEFT THE GROUP CHAT ON LEVEL " + Game.level);
                case 8 -> txtLvl.setText("LEVELER HAS SPIRIT, BUT GAVE IT UP ON LEVEL " + Game.level);
                case 9 -> txtLvl.setText("LEVELER FELT 5 STAGES OF GRIEF ON LEVEL " + Game.level);
                case 10 -> txtLvl.setText("LEVEL " + Game.level + " PUT LEVELER TO REST");
                case 11 -> txtLvl.setText("LEVELER, IT'S LEVEL " + Game.level + ". STRIVE FOR BETTER");
                case 12 -> txtLvl.setText("LEVELER WAS 'SOLO-LEVELING' ON LEVEL " + Game.level);
                case 13 -> txtLvl.setText("YOU FELT SKILL ISN'T YOUR THING ON LEVEL " + Game.level);
                case 14 -> txtLvl.setText("WOW, LEVELER GOT PATRONIZED ON LEVEL " + Game.level);
                case 15 -> txtLvl.setText("LEVELER, GAMERAGE IS WHAT YOU FEEL ON LEVEL " + Game.level);
            }
        }
        else if (Game.level >= 16 && Game.level <= 25) {
            switch(randomCasePro) {
                case 16 -> txtLvl.setText("LEVELER LEFT FOR KEBAB PIZZA ON LEVEL " + Game.level);
                case 17 -> txtLvl.setText("LEVELER HELD THE LINE UP UNTIL LEVEL " + Game.level);
                case 18 -> txtLvl.setText("LEVELER BUMPED INTO AUTOMATONS ON LEVEL " + Game.level);
                case 19 -> txtLvl.setText("LEVELER WENT MAINCHARACTER ON LEVEL " + Game.level);
                case 20 -> txtLvl.setText("LEVELER BECAME A LEGEND ON LEVEL " + Game.level);
                case 21 -> txtLvl.setText("LEVELER WAS 'PRO-GAMING' UNTIL " + Game.level);
                case 22 -> txtLvl.setText("LEVELER EMBRACED DESERVED REST ON LEVEL " + Game.level);
                case 23 -> txtLvl.setText("LEVELER LEFT FOR SWEDISH FIKA ON LEVEL " + Game.level);
                case 24 -> txtLvl.setText("LEVELER LEFT TO SIGN AUTOGRAPHS ON LEVEL " + Game.level);
                case 25 -> txtLvl.setText("LEVELER LEFT THE MATRIX ON LEVEL " + Game.level);
            }
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
