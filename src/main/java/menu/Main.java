package menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image levelerIcon = new Image(getClass().getResourceAsStream("/images/Leveler_Icon.png"));        
        stage.setTitle("Leveler");
        stage.getIcons().add(levelerIcon);
        stage.setScene(scene);
        stage.show();
    }
}
