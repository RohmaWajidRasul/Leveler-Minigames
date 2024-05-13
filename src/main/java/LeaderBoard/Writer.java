package LeaderBoard;

import java.io.FileWriter;
import java.io.IOException;

public class Writer extends FileWriter {

    public Writer(String string) throws IOException {
        super(string);
    }

    public void writeFile(String string) throws IOException {
        this.write(string);

    }
}
