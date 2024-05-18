package LeaderBoard;

public class Parser {

    public Parser() {}

    public String[] split(String string) {
        return string.split("\\r?\\n");
    }

    public String[] splitAtComma(String string) {
        return string.split(",");
    }

}
