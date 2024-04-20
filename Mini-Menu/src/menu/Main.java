package menu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Platform.runLater(() -> {
                try {
                    URL url = new File("Mini-Menu/src/menu/Menu.fxml").toURI().toURL();
                    Parent root = FXMLLoader.load(url);

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Leveler");
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
