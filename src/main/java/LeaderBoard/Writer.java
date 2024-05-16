package LeaderBoard;

import java.io.FileWriter;
import java.io.IOException;

public class Writer extends FileWriter {

    public Writer(String fileName) throws IOException {
        super(fileName);
    }

    public void writeFile(String fileName) throws IOException {
        this.write(fileName);

    }
}
