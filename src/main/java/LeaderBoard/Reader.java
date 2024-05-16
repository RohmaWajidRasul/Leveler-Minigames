package LeaderBoard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader extends FileReader {


    public Reader(String fileName) throws FileNotFoundException {
        super(fileName);
    }
    public String readFile() throws IOException {

        int read = this.read();
        StringBuilder output = new StringBuilder();

        while(read != -1) {
            output.append((char) read);
            read = this.read();
        }

        return output.toString();
    }
}
