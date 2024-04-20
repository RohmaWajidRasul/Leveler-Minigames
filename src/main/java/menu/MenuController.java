package menu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MenuController {
    @FXML
    private void handleRegisterSceneButton(ActionEvent event) {
        try {
            URL url = new File("Leveler/src/main/resources/Register.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene manualScene = new Scene(root);

            Node sourceNode = (Node) event.getSource();
            Stage currentStage = (Stage) sourceNode.getScene().getWindow();

            currentStage.setScene(manualScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTutorialSceneButton(ActionEvent event) {
        try {
            URL url = new File("Leveler/src/main/resources/Tutorial.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene manualScene = new Scene(root);

            Node sourceNode = (Node) event.getSource();
            Stage currentStage = (Stage) sourceNode.getScene().getWindow();

            currentStage.setScene(manualScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExitButton(ActionEvent event) {
        Platform.exit();
    }
}
