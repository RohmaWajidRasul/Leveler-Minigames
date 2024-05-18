package gameover;

import java.io.IOException;
import LeaderBoard.Leaderboard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import menu.Game;

public class GameOverScreen {
    
    public void runGameOver(Stage gameOverStage) throws IOException {
        FXMLLoader gameOverLoader = new FXMLLoader(getClass().getResource("/gameoverFXMLs/GameOver.fxml"));
        Scene gameOverScene = new Scene(gameOverLoader.load());
        gameOverStage.setScene(gameOverScene);
        gameOverStage.show();
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.onDeath(Game.level);
        GameOverController goController = gameOverLoader.getController();
        goController.showLevel();
    }
}
