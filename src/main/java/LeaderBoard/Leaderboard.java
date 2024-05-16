package LeaderBoard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Leaderboard {

    public static String playerName;
    private final String fileName;
    ArrayList<Entry> entries;
    public Leaderboard() {
        this.entries = new ArrayList<Entry>();
        this.fileName = "src/main/resources/menu/names.txt";
    }

    public void onDeath(int newScore) {


        try {
            fillArray();
            if (playerName != null) {
                this.entries.add(new Entry(playerName,newScore));
            }

            insertEntry();

            Writer writer = new Writer(fileName);

            String newScores;
            for (Entry entry : this.entries) {
                newScores = entry.getName() + "," + entry.getScore() + "\n";
                writer.writeFile(newScores);
            }
            writer.close();



        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void fromMenu(Stage stage) {

        fillArray();

        StackPane stackpane = new StackPane();
        Scene scene = new Scene(stackpane,600,400);
        int y = -150;
        for (int i = 0; i < 5; i++) {
            Text score = new Text (this.entries.get(i).toString());
            score.setTranslateY(y);

            score.setFont(Font.font("Georgia", 20));
            //I don't know much about algebra, but I know -150 + 50 = -100
            y = y + 50;

            stackpane.getChildren().add(score);
        }

        Button button = new Button("Back to menu");
        button.setTranslateY(150);

        stackpane.getChildren().add(button);

        button.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Menu.fxml"));
            Scene Menuscene;
            try {
                Menuscene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Leveler");
            stage.setScene(Menuscene);
            stage.show();
        });

        stage.setScene(scene);

    }

    private void fillArray() {


        try(Reader reader = new Reader(this.fileName)) {

            String fileContent = reader.readFile();

            Parser parser = new Parser();
            String[] currentScores = parser.split(fileContent);

            String[] singleEntry;
            String name;
            int score;

            for (String string : currentScores) {
                singleEntry = parser.splitAtComma(string);
                name = singleEntry[0];
                score = Integer.parseInt(singleEntry[1]);

                this.entries.add(new Entry(name,score));
            }


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void insertEntry() {
        int length = this.entries.size();
        Entry entry = this.entries.remove(this.entries.size() - 1);

        int i = 0;
        while(i <= this.entries.size() && length > this.entries.size()) {

            if (i == this.entries.size() || entry.getScore() >= this.entries.get(i).getScore()) {
                this.entries.add(i,entry);

            }
            i++;
        }
    }
}
