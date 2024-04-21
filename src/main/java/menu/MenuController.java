package menu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private void handleRegisterSceneButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/menu/Register.fxml"));
        Parent root = fxmlLoader.load();
        Scene registerScene = new Scene(root);
        Node sourceNode = (Node) event.getSource();
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();
        currentStage.setScene(registerScene);
    }

    @FXML
    private void handleExitButton(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void handlePlayButton(ActionEvent event) {

    }
}
