package gameover;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameOverScreen {
    
    public void runGameOver(Stage gameOverStage) throws IOException {
        FXMLLoader gameOverLoader = new FXMLLoader(getClass().getResource("/gameoverFXMLs/GameOver.fxml"));
        Scene gameOverScene = new Scene(gameOverLoader.load());
        gameOverStage.setScene(gameOverScene);
        gameOverStage.show();
        GameOverController goController = gameOverLoader.getController();
        goController.showlevel();
    }
}
