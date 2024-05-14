package LeaderBoard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.Main;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Leaderboard {

    public static String playerName;
    ArrayList<Entry> entries;
    public Leaderboard() {
        entries = new ArrayList<Entry>();
    }

    public void onDeath(int newScore) {
        String filename = "src/main/resources/menu/names.txt";

        try {
            fillArray();
            if (playerName != null) {
                entries.add(new Entry(playerName,newScore));
            }

            insertEntry();

            Writer writer = new Writer(filename);

            String newScores;
            for (Entry entry : entries) {
                newScores = entry.name + "," + entry.score + "\n";
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
            Text score = new Text (entries.get(i).toString());
            score.setTranslateY(y);

            score.setFont(Font.font("Georgia", 20));
            y += 50;


            stackpane.getChildren().addAll(score);

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


        }

        stage.setScene(scene);

    }

    private void fillArray() {
        String filename = "src/main/resources/menu/names.txt";

        try(Reader reader = new Reader(filename)) {

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

                entries.add(new Entry(name,score));
            }


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void insertEntry() {
        int length = entries.size();
        Entry entry = entries.remove(entries.size() - 1);

        int i = 0;
        while(i <= entries.size() && length > entries.size()) {

            if (i == entries.size() || entry.score >= entries.get(i).score) {
                entries.add(i,entry);

            }
            i++;
        }
    }
}
