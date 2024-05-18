package menu;

import LeaderBoard.Leaderboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterController {
    @FXML
    private void handleRegisterBackButton(ActionEvent event) throws IOException {
        backToMainMenu(event);
    }

    @FXML
    private void backToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/menu/Menu.fxml"));
        Scene scene = new Scene(root);
        Node sourceNode = (Node) event.getSource();
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();
        currentStage.setScene(scene);
    }

    @FXML
    private TextField nameField;

    @FXML
    private void handleRegisterConfirmButton(ActionEvent event) throws IOException {
        String name = nameField.getText();
        if (!name.isEmpty() && !name.contains(",")) {
            saveName(name);
            backToMainMenu(event);
        } else {
            System.out.println("error");
        }
    }

    private void saveName(String name) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/resources/menu/names.txt", true))) {
            //writer.println(name);
            Leaderboard.playerName = name;
            System.out.println("Name saved: " + name);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }


}
