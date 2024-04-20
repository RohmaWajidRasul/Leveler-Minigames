package menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

public class RegisterController {
    @FXML
    private void handleRegisterBackButton(ActionEvent event) {
        backToMainMenu(event);
    }

    private void backToMainMenu(ActionEvent event) {
        try {
            URL url = new File("Mini-Menu/src/menu/Menu.fxml").toURI().toURL();
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
    private TextField nameField;

    @FXML
    private void handleRegisterConfirmButton(ActionEvent event) {
        String name = nameField.getText();
        if (!name.isEmpty()) {
            saveName(name);
            backToMainMenu(event);
        } else {
            System.out.println("error");
        }
    }

    private void saveName(String name) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Mini-Menu/src/menu/names.txt", true))) {
            writer.println(name);
            System.out.println("Name saved: " + name);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }


}
